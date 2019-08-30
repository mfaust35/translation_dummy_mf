package com.faust.m.td.koin

import android.app.Activity
import androidx.test.espresso.intent.rule.IntentsTestRule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

/**
 * KoinIntentTestRule is meant to be used as an IntentTestRule with the extra feature
 * of loading a koin module before the activity is launched and unloading the same module after
 * the activity is finished. You can also add a lambda which will be called before activity is
 * launched, in which you can stub mockk that requires early setup
 * @param activityClass : will be passed to ActivityTestRule
 * @param module : the module to load in koin
 * @param stubMockk : if needed, use this lambda to stub mockk that require early setup
 */
class KoinIntentsTestRule<T: Activity>(
    activityClass: Class<T>,
    private var module: Module,
    private var stubMockk: (() -> Unit)? = null): IntentsTestRule<T>(activityClass) {

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