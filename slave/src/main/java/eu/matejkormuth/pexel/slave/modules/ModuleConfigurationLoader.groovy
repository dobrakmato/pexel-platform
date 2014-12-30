package eu.matejkormuth.pexel.slave.modules

/**
 *
 */
class ModuleConfigurationLoader {
    File basePath

    def load(String name) {
        return new ConfigSlurper().parse(new File(basePath.getAbsolutePath() + "/" + name).toURI().toURL());
    }

    def save(String name, Writable config) {
        new File(basePath.getAbsolutePath() + "/" + name).withWriter { writer ->
            config.writeTo(writer)
        }
    }
}
