package code.service.input;

import code.model.InputData;
import code.model.OverpaymentData;
import code.model.OverpaymentType;
import code.model.RateType;
import code.model.Reference;
import code.model.exception.MortgageException;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Value
@Service
public class InputFromFile implements InputServiceI {
    private static final Path READ_FILE_PATH = Path.of("src/main/resources/InputData.txt");

    @Override
    public InputData load() {
        try {
            return read();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error: [%s] while loading InputData from Path: [%s]"
                            .formatted(e.getMessage(), READ_FILE_PATH.toString()));
        }
    }

    public static InputData read() throws IOException {
        Map<String, String> arguments = Files.readString(READ_FILE_PATH)
                .lines()
                .map(e -> e.split(";"))
                .filter(e -> e.length == 2)
                .collect(Collectors.toMap(k -> k[0], v -> v[1]));

        BigDecimal startingAmount = Optional.ofNullable(arguments.get("startingAmount")).map(BigDecimal::new)
                .orElseThrow(() -> new MortgageException("startingAmount is null"));
        BigDecimal startingDuration = Optional.ofNullable(arguments.get("startingDuration")).map(BigDecimal::new)
                .orElseThrow(() -> new MortgageException("startingDuration is null"));
        BigDecimal interestPercent = Optional.ofNullable(arguments.get("interestPercent")).map(BigDecimal::new)
                .orElseThrow(() -> new MortgageException("interestPercent is null"));
        return InputData.builder()
                .reference(new Reference(startingAmount, startingDuration, interestPercent))
                .repaymentStartDate(
                        Optional.ofNullable(arguments.get("repaymentStartDate")).map(LocalDate::parse)
                                .orElseThrow(() -> new MortgageException("repaymentStartDate is null")))
                .startingAmount(startingAmount)
                .startingDuration(startingDuration)
                .interestPercent(interestPercent)
                .rateType(
                        Optional.ofNullable(arguments.get("rateType")).map(RateType::valueOf)
                                .orElseThrow(() -> new MortgageException("rateType is null")))
                .overpaymentProvisionPercent(
                        Optional.ofNullable(arguments.get("overpaymentProvisionPercent")).map(BigDecimal::new)
                                .orElseThrow(() -> new MortgageException("overpaymentProvisionPercent is null")))
                .overpaymentProvisionMonths(
                        Optional.ofNullable(arguments.get("overpaymentProvisionMonths")).map(BigDecimal::new)
                                .orElseThrow(() -> new MortgageException("overpaymentProvisionMonths is null")))
                .overpaymentMap(
                        Optional.ofNullable(arguments.get("overpaymentMap")).map(InputFromFile::mapOverpaymentMap)
                                .orElseThrow(() -> new MortgageException("overpaymentMap is null")))
                .doPrint(
                        Optional.ofNullable(arguments.get("doPrint")).map(Boolean::valueOf)
                                .orElseThrow(() -> new MortgageException("doPrint is null")))
                .doSerialize(
                        Optional.ofNullable(arguments.get("doSerialize")).map(Boolean::valueOf)
                                .orElseThrow(() -> new MortgageException("doSerialize is null")))
                .doCalculateSummary(
                        Optional.ofNullable(arguments.get("doCalculateSummary")).map(Boolean::valueOf)
                                .orElseThrow(() -> new MortgageException("doCalculateSummary is null")))
                .calculationCycle(
                        Optional.ofNullable(arguments.get("calculationCycle")).map(BigDecimal::new)
                                .orElseThrow(() -> new MortgageException("calculationCycle is null")))
                .doAddYearSeparator(
                        Optional.ofNullable(arguments.get("doAddYearSeparator")).map(Boolean::valueOf)
                                .orElseThrow(() -> new MortgageException("doAddYearSeparator is null")))
                .build();
    }

    // (month=amount:type),(),()
    private static Map<BigDecimal, OverpaymentData> mapOverpaymentMap(String stringMap) {
        return Arrays.stream(stringMap.split(","))
                .map(e -> e.split("="))
                .collect(Collectors.toMap(
                        k -> new BigDecimal(k[0]),
                        v -> {
                            String[] overpaymentDataValues = v[1].split(":");
                            return OverpaymentData.of(
                                    new BigDecimal(overpaymentDataValues[0]),
                                    OverpaymentType.valueOf(overpaymentDataValues[1])
                            );
                        },
                        (l, r) -> OverpaymentData.of(
                                l.getAmount().add(r.getAmount()),
                                l.getOverpaymentType()),
                        TreeMap::new
                ));
    }
}