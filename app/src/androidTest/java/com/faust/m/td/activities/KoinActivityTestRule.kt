package com.faust.m.td.activities

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

/**
 * KoinActivityTestRule is meant to be used as an ActivityTestRule with the extra feature
 * of loading a koin module before the activity is launched and unloading the same module after
 * the activity is finished. You can also add a lambda which will be called before activity is
 * launched, in which you can stub mockk that requires early setup
 *
 * @see KoinIntentsTestRule : code duplication here, I don't know how to get rid of it
 * Using KoinIntentsTestRule everywhere seems too hacky
 * // TODO remove code duplication
 *
 * @param activityClass : will be passed to ActivityTestRule
 * @param module : the module to load in koin
 * @param stubMockk : if needed, use this lambda to stub mockk that require early setup
 */
class KoinActivityTestRule<T: Activity>(
    activityClass: Class<T>,
    private var module: Module,
    private var stubMockk: (() -> Unit)? = null): ActivityTestRule<T>(activityClass) {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        loadKoinModules(module)
        stubMockk?.invoke()
    }

    override fun afterActivityFinished() {
        super.afterActivityFinished()
        unloadKoinModules(module)
    }
}