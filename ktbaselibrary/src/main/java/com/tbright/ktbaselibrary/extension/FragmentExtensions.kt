package com.tbright.ktbaselibrary.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


fun Fragment.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    childFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(id, fragment).commit()
}

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





