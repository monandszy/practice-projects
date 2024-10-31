package code;

import code.commandBuilder.ArgumentAdjusterImpl;
import code.commandBuilder.ArgumentExtractorImpl;
import code.commandBuilder.CommandBuilderImpl;
import code.commandBuilder.CommandTypeFactoryImpl;
import code.commandBuilder.QueryAdjusterImpl;
import code.commandBuilder.QueryFactoryImpl;
import code.commandBuilder.SplitterImpl;
import code.databaseRunner.DatabaseRunnerImpl;

import java.util.List;
import java.util.Scanner;


public class ToDoRunner {

    public static void main(String[] args) {

        List<String> stringCommands = List.of(
                "DELETE ALL;",
                "CREATE;NAME=TASK1;DESCRIPTION=SOME DESCRIPTION1;DEADLINE=11.02.2021 20:10;PRIORITY=0",
                "CREATE;NAME=TASK2;DESCRIPTION=SOME DESCRIPTION2;DEADLINE=12.02.2021 20:10;PRIORITY=1",
                "CREATE;NAME=TASK3;DESCRIPTION=SOME DESCRIPTION3;DEADLINE=12.02.2021 20:10;PRIORITY=2",
                "CREATE;NAME=TASK4;DESCRIPTION=SOME DESCRIPTION4;DEADLINE=14.02.2021 20:10;PRIORITY=3",
                "CREATE;NAME=TASK5;DESCRIPTION=SOME DESCRIPTION5;DEADLINE=14.02.2021 20:10;PRIORITY=4",
                "READ;NAME=TASK5",
                "COMPLETED;NAME=TASK1",
                "READ ALL;",
                "UPDATE;NAME=TASK2;DESCRIPTION=SOME DESCRIPTION NEW;DEADLINE=12.02.2021 10:10;PRIORITY=81",
                "READ ALL;",
                "READ ALL;SORT=DEADLINE,DESC",
                "COMPLETED;NAME=TASK3",
                "READ ALL;",
                "READ GROUPED;",
                "DELETE;NAME=TASK2",
                "READ ALL;"
        );

//        getDatabaseRunner().createTable(TableData.CREATE_TABLE_QUERY, TableData.ALTER_TABLE_QUERY);

//        runListCommandProcessor(stringCommands);
        runScannerCommandProcessor();
    }

    private static void runListCommandProcessor(List<String> stringCommands) {
        CommandProcessorListImpl commandProcessorList = new CommandProcessorListImpl(
                stringCommands, getCommandBuilder(), getDatabaseRunner()
        );
        commandProcessorList.processCommands();
    }

    private static void runScannerCommandProcessor() {
        CommandProcessor scannerCommandProcessor = new CommandProcessorScannerImpl(
                new Scanner(System.in),
                getCommandBuilder(),
                getDatabaseRunner()
        );
        scannerCommandProcessor.processCommands();
    }

    private static DatabaseRunnerImpl getDatabaseRunner() {
        return new DatabaseRunnerImpl(
                "jdbc:postgresql://localhost:5432/todo_list",
                "postgres",
                "meow"
        );
    }

    private static CommandBuilderImpl getCommandBuilder() {
        return new CommandBuilderImpl(
                new SplitterImpl(),
                new CommandTypeFactoryImpl(),
                new ArgumentExtractorImpl(new SplitterImpl()),
                new QueryFactoryImpl(),
                new ArgumentAdjusterImpl(),
                new QueryAdjusterImpl()
        );
    }
}
