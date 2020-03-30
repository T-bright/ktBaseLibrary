package com.tbright.ktbaselibrary.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

fun FragmentActivity.addFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(id, fragment).commit()
}

fun FragmentActivity.addFragment(tag: String, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(fragment, tag).commit()
}

fun FragmentActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(id, fragment).commit()
}

fun FragmentActivity.findFragmentByTag(tag: String): Fragment? =
    supportFragmentManager.findFragmentByTag(tag)

fun FragmentActivity.findFragmentById(@IdRes id: Int): Fragment? =
    supportFragmentManager.findFragmentById(id)

fun FragmentActivity.showFragment(@IdRes id: Int, currentFragment: Fragment, needShowFragment: Fragment) {
    var transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    transaction.hide(currentFragment)
    if (needShowFragment.isAdded) {
        transaction.show(needShowFragment).commit()
    } else {
        transaction.add(id, needShowFragment).show(needShowFragment).commit()
    }
}

fun FragmentActivity.showFragment(@IdRes id: Int, needShowFragment: Fragment) {
    var transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    if (needShowFragment.isAdded) {
        transaction.show(needShowFragment).commit()
    } else {
        transaction.add(id, needShowFragment).show(needShowFragment).commit()
    }
}

fun FragmentActivity.showFragment(tag: String, needShowFragment: Fragment) {
    var transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    if (needShowFragment.isAdded) {
        transaction.show(needShowFragment).commit()
    } else {
        transaction.add(needShowFragment, tag).show(needShowFragment).commit()
    }
}
