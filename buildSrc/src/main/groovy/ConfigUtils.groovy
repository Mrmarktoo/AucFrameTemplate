import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.invocation.Gradle

class ConfigUtils {

    static init(Gradle gradle) {
        generateDep(gradle)
        addCommonGradle(gradle)
        TaskDurationUtils.init(gradle)
    }

    /**
     * 根据 depConfig 生成 dep
     */
    private static void generateDep(Gradle gradle) {
        def configs = [:]
        for (Map.Entry<String, DepConfig> entry : Config.depConfig.entrySet()) {
            def (name, config) = [entry.key, entry.value]// key 为依赖配置中键值， value 为DepConfig对象
            if (name.startsWith("plugin_")) {// Android build plugin 配置
                config.dep = config.pluginPath
            } else {
                if (config.useLocal) {
                    config.dep = gradle.rootProject.findProject(config.localPath)
                } else {
                    config.dep = config.remotePath
                }
            }
            configs.put(name, config)
        }
        GLog.l("generateDep = ${GLog.object2String(configs)}")
    }

    private static buildOrder = 0

    private static addCommonGradle(Gradle gradle) {
        buildOrder = 0
        GLog.l("在 项目构建过程中，每个模块构建前，应用欲置的gradle脚本. 此处为增加项目构建监听。")
        gradle.addProjectEvaluationListener(new ProjectEvaluationListener() {
            @Override
            void beforeEvaluate(Project project) {
                // 在 project 的 build.gradle 前 do sth.
                buildOrder++
                GLog.l("模块构建开始 [$buildOrder]-[$project.displayName]")
                if (project.subprojects.isEmpty()) {
                    if (project.path.contains(":plugin:")) {
                        return
                    }
                    if (project.name == "app") {
                        GLog.l(project.toString() + " applies buildApp.gradle")
                        project.apply {
                            from "${project.rootDir.path}/buildApp.gradle"
                        }
                    } else {
                        GLog.l(project.toString() + " applies buildLib.gradle")
                        project.apply {
                            from "${project.rootDir.path}/buildLib.gradle"
                        }
                    }
                }
            }

            @Override
            void afterEvaluate(Project project, ProjectState state) {
                // 在 project 的 build.gradle 末 do sth.
                GLog.l("模块构建完成 [$project.displayName]")
            }
        })
    }

    /**build.gradle 添加Android build plugin*/
    static getApplyPlugins() {
        def plugins = [:]
        for (Map.Entry<String, DepConfig> entry : Config.depConfig.entrySet()) {
            if (entry.value.isApply && entry.key.startsWith("plugin_")) {
                plugins.put(entry.key, entry.value)
            }
        }
        GLog.d("getApplyPlugins = ${GLog.object2String(plugins)}")
        return plugins
    }

    /**app module build.gradle 添加Android dependency module*/
    static getApplyPkgs() {
        def pkgs = [:]
        for (Map.Entry<String, DepConfig> entry : Config.depConfig.entrySet()) {
            if (entry.value.isApply && entry.key.endsWith("_pkg")) {
                pkgs.put(entry.key, entry.value)
            }
        }
        GLog.d("getApplyPkgs = ${GLog.object2String(pkgs)}")
        return pkgs
    }

    /**module build.gradle 添加Android dependency*/
    static getApplyExports() {
        def exports = [:]
        for (Map.Entry<String, DepConfig> entry : Config.depConfig.entrySet()) {
            if (entry.value.isApply && entry.key.endsWith("_export")) {
                exports.put(entry.key, entry.value)
            }
        }
        GLog.d("getApplyExports = ${GLog.object2String(exports)}")
        return exports
    }
}