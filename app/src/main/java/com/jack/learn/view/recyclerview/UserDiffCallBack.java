package com.jack.learn.view.recyclerview;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.jack.learn.thirdlib.UserInfo;

import java.util.List;

public class UserDiffCallBack extends DiffUtil.Callback {

    private List<UserInfo> oldList;
    private List<UserInfo> newList;

    public UserDiffCallBack(List<UserInfo> oldList,List<UserInfo> newList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    /**
     * 判断旧数据集中的某个元素和新数据集中的某个元素是否代表同一个实体。
     * 返回true才能执行areContentsTheSame
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    /**
     * 判断旧数据集中的某个元素和新数据集中的某个元素的内容是否相同。
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the
     *                        oldItem
     * @return
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        UserInfo oldUserInfo = oldList.get(oldItemPosition);
        UserInfo newUserInfo = newList.get(newItemPosition);
        return oldUserInfo.getId() == newUserInfo.getId() && oldUserInfo.getName().equals(newUserInfo.getName())
                && oldUserInfo.getAge() == newUserInfo.getAge();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
