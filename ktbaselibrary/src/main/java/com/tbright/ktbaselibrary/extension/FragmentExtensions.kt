package com.tbright.ktbaselibrary.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


fun Fragment.addFragment(@IdRes id: Int, fragment: Fragment) {
    childFragmentManager.beginTransaction().add(id, fragment).commit()
}

fun Fragment.addFragment(tag: String, fragment: Fragment) {
    childFragmentManager.beginTransaction().add(fragment, tag).commit()
}

fun Fragment.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    childFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(id, fragment).commit()
}

fun Fragment.findFragmentByTag(tag: String): Fragment? =
    childFragmentManager.findFragmentByTag(tag)

fun Fragment.findFragmentById(@IdRes id: Int): Fragment? =
    childFragmentManager.findFragmentById(id)


fun Fragment.showFragment(@IdRes id: Int, currentFragment: Fragment, needShowFragment: Fragment) {
    var transaction = childFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    transaction.hide(currentFragment)
    if (needShowFragment.isAdded) {
        transaction.show(needShowFragment).commit()
    } else {
        transaction.add(id, needShowFragment).show(needShowFragment).commit()
    }
}

fun Fragment.showFragment(@IdRes id: Int, needShowFragment: Fragment) {
    var transaction = childFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    if (needShowFragment.isAdded) {
        transaction.show(needShowFragment).commit()
    } else {
        transaction.add(id, needShowFragment).show(needShowFragment).commit()
    }
}

fun Fragment.showFragment(tag: String, needShowFragment: Fragment) {
    var transaction = childFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    if (needShowFragment.isAdded) {
        transaction.show(needShowFragment).commit()
    } else {
        transaction.add(needShowFragment, tag).show(needShowFragment).commit()
    }
}




