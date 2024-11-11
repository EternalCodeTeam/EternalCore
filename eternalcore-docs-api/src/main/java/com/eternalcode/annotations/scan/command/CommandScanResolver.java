package com.eternalcode.annotations.scan.command;

import com.eternalcode.annotations.scan.EternalScanRecord;
import com.eternalcode.annotations.scan.EternalScanResolver;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.permission.Permissions;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.command.Command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class CommandScanResolver implements EternalScanResolver<CommandResult> {

    @Override
    public List<CommandResult> resolve(EternalScanRecord record) {
        Class<?> type = record.clazz();

        Command annotation = type.getAnnotation(Command.class);

        if (annotation != null) {
            return this.handleCommand(record, annotation);
        }

        RootCommand rootRoute = type.getAnnotation(RootCommand.class);

        if (rootRoute != null) {
            return this.handleRootRoute(record, rootRoute);
        }

        return List.of();
    }

    private List<CommandResult> handleCommand(EternalScanRecord record, Command command) {
        List<CommandResult> results = new ArrayList<>();

        for (Method method : record.methods()) {
            Execute execute = method.getAnnotation(Execute.class);

            if (execute == null) {
                continue;
            }

            String name = command.name() + " " + execute.name();
            List<String> aliases = Arrays.stream(execute.aliases())
                .map(alias -> command.name() + ", " + alias)
                .toList();

            results.add(this.handleExecutor(record, method, name, aliases));
        }

        return removeDuplicates(results);
    }

    private List<CommandResult> handleRootRoute(EternalScanRecord record, RootCommand rootCommand) {
        List<CommandResult> results = new ArrayList<>();

        for (Method method : record.methods()) {
            Execute execute = method.getAnnotation(Execute.class);

            if (execute == null) {
                continue;
            }

            String name = execute.name();
            List<String> aliases = Arrays.asList(execute.aliases());

            results.add(this.handleExecutor(record, method, name, aliases));
        }

        return removeDuplicates(results);
    }

    private CommandResult handleExecutor(EternalScanRecord record, Method method, String name, List<String> aliases) {
        Class<?> clazz = record.clazz();
        Set<String> permissions = new HashSet<>();

        permissions.addAll(this.scan(Permission.class, clazz, method, (Permission::value)));
        permissions.addAll(this.scan(Permissions.class, clazz, method, (permissionsAnnotation -> {
            List<String> permissionsList = new ArrayList<>();

            for (Permission permission : permissionsAnnotation.value()) {
                permissionsList.addAll(Arrays.asList(permission.value()));
            }

            return permissionsList.toArray(new String[0]);
        })));

        List<String> description = this.scan(DescriptionDocs.class, Object.class, method, (DescriptionDocs::description));
        List<String> arguments = this.scan(DescriptionDocs.class, Object.class, method, (DescriptionDocs::arguments));

        return new CommandResult(
            name,
            aliases,
            List.copyOf(permissions),
            description,
            arguments
        );
    }

    private <A extends Annotation, R> List<R> scan(Class<A> annotationClass, Class<?> type, Method method, Function<A, R[]> resolver) {
        List<R> results = new ArrayList<>();

        A annotation = type.getAnnotation(annotationClass);

        if (annotation != null) {
            results.addAll(List.of(resolver.apply(annotation)));
        }

        A methodAnnotation = method.getAnnotation(annotationClass);

        if (methodAnnotation != null) {
            results.addAll(List.of(resolver.apply(methodAnnotation)));
        }

        return results;
    }

    public static <T> List<T> removeDuplicates(List<T> list) {
        return new ArrayList<>(new HashSet<>(list));
    }

}
