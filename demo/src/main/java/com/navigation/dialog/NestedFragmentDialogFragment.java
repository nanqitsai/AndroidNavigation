package com.navigation.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.navigation.R;

import me.listenzz.navigation.AwesomeFragment;
import me.listenzz.navigation.FragmentHelper;
import me.listenzz.navigation.PresentAnimation;

public class NestedFragmentDialogFragment extends AwesomeFragment {

    @Override
    public boolean isParentFragment() {
        return true;
    }

    @Override
    protected AwesomeFragment childFragmentForAppearance() {
        return getContentFragment();
    }

    @Override
    public int getPresentContainerId() {
        return  R.id.nested_content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nested_dialog, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            if (contentFragment == null) {
                throw new IllegalArgumentException("必须先通过 `setContentFragment` 指定 contentFragment");
            }
            FragmentHelper.addFragmentToBackStack(getChildFragmentManager(), R.id.nested_content, contentFragment, PresentAnimation.None);
        }
    }

    private AwesomeFragment contentFragment;

    public void setContentFragment(AwesomeFragment fragment) {
        if (isAdded()) {
            throw new IllegalStateException("NavigationFragment 已经出于 added 状态，不可以再设置 rootFragment");
        }
        contentFragment = fragment;
    }

    public AwesomeFragment getContentFragment() {
        if (isAdded()) {
            return (AwesomeFragment) getChildFragmentManager().findFragmentById(R.id.nested_content);
        }
        return null;

    }
}
