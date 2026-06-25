package dev.teogor.stitch.catalog.demo.di

import dev.teogor.stitch.catalog.demo.data.local.data.repository.TaskRepository

/**
 * A type-safe blueprint representing the application's top-level
 * dependency graph, independent of specific injection engines.
 */
public interface ApplicationComponent {
    public val taskRepository: TaskRepository

    public companion object {
        /**
         * Factory tracking hook returning the active runtime implementation.
         */
        public operator fun invoke(): ApplicationComponent = StubApplicationComponent
    }
}
