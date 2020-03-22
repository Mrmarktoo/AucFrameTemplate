// 依赖文件配置脚本
class Config {

    static applicationId = 'com.blankj.aucframe'            // TODO: MODIFY
    static appName = 'AucFrame'                             // TODO: MODIFY

    static compileSdkVersion = 28                           // TODO: MODIFY
    static minSdkVersion = 21                               // TODO: MODIFY
    static targetSdkVersion = 28                            // TODO: MODIFY
    static versionCode = 1_000_000                          // TODO: MODIFY
    static versionName = '1.0.0'// E.g. 1.9.72 => 1,009,072 // TODO: MODIFY

    static kotlin_version = '1.3.50'
    static support_version = '27.1.1'
    static leakcanary_version = '1.6.3'

    static depConfig = [
            /*Never delete this line*/
            /*Never delete this line*/
//            feature_template_app       : new DepConfig(":feature:template:app"),
//            feature_template_pkg       : new DepConfig(":feature:template:pkg"),
//            feature_template_export    : new DepConfig(":feature:template:export"),
            plugin_gradle              : new DepConfig(pluginPath: "com.android.tools.build:gradle:3.5.3"),
            plugin_kotlin              : new DepConfig(pluginPath: "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"),
            plugin_api                 : new DepConfig(pluginPath: "com.blankj:api-gradle-plugin:1.1", pluginId: "com.blankj.api"),
            plugin_bus                 : new DepConfig(pluginPath: "com.blankj:bus-gradle-plugin:2.3", pluginId: "com.blankj.bus"),

            support_appcompat_v7       : new DepConfig("com.android.support:appcompat-v7:$support_version"),
            support_design             : new DepConfig("com.android.support:design:$support_version"),
            support_multidex           : new DepConfig("com.android.support:multidex:1.0.2"),
            support_constraint         : new DepConfig("com.android.support.constraint:constraint-layout:1.1.3"),

            kotlin                     : new DepConfig("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"),
            utilcode                   : new DepConfig("com.blankj:utilcode:1.25.10-alpha5"),
            free_proguard              : new DepConfig("com.blankj:free-proguard:1.0.1"),
            swipe_panel                : new DepConfig("com.blankj:swipe-panel:1.1"),

            leakcanary_android         : new DepConfig("com.squareup.leakcanary:leakcanary-android:$leakcanary_version"),
            leakcanary_android_no_op   : new DepConfig("com.squareup.leakcanary:leakcanary-android-no-op:$leakcanary_version"),
            leakcanary_support_fragment: new DepConfig("com.squareup.leakcanary:leakcanary-support-fragment:$leakcanary_version"),
    ]
}