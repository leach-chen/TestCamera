package com.example.testcamera;

/**
 * Created by admin1 on 2017/7/5.
 */


public interface FusionType {

  interface Message{
    /**
     * 图片下载完成
     */
    int IMAGE_DOWN = 100;
    /**
     * 获取联系人列表
     */
    int GETS_CONTACTS_LIST = 11;
    /**
     * 发送消息
     */
    int SEND_THE_MESSAGE_SUCCESS = 13;

    /**
     * 接受消息
     */
    int ACCEPT_THE_MESSAGE_SUCCESS = 14;
    /**
     * 获得群组列表
     */
    int GETS_GROUP_LIST = 15;
    /**
     * 获得群组成员列表
     */
    int GET_GROUP_MEMBERS = 16;
    /**
     * 新的电话
     */
    int INCOMING_CALL = 17;
    /**
     * 挂电话
     */
    int BLOCKING_PHONE = 18;
    /**
     * 添加好友成功
     */
    int ADD_FRIEND_SUCCESS = 19;
    /**
     * 查询好友信息成功
     */
    int QUERY_FRIEND_INFO_SUCCESS = 20;
    /**
     * 添加好友失败
     */
    int ADD_FRIEND_FAILURE = 21;
    /**
     * 别人添加好友
     */
    int HAS_SOMEBODY_ADDFRIEND = 22;
    /**
     * 请求好友发送成功
     */
    int REQUEST_FRIEND_SUCCESS = 23;
    /**
     * 下一步
     */
    int BUTTON_HAS_CHANGE = 24;

    /**
     * 从相册选择图片
     */
    int SEND_PIC_FROM_ALBUM = 25;

    /**
     * 刷新进度条
     */
    int REFRESH_PROGRESSBAR = 26;

    /**
     * 上传文件成功
     */
    int UPDATE_FILE_SUCCESS = 27;
    /**
     * 下载图片成功
     */
    int DOWNLOAD_PIC_SUCCESS = 28;
    /**
     * 声音模式
     */
    int SOUND_PATTERN = 29;

    /**
     * 发送消息失败
     */
    int SEND_ALLMSG_FAILURE = 30;

    /**
     * 重新发送消息
     */
    int REPEAT_THE_MESSAGE = 31;

    /**
     * 获得群组消息失败
     */
    int GETS_GROUP_LIST_FAILURE = 32;

    /**
     * 收到直播通知
     */
    int NEW_LIVEVIDEO = 33;
  }

  /**
   * 界面动作
   *
   * @author wu_tian
   */
  interface ActionMsg {

    /**
     * 跳转系统相册回调码
     */
    int JUMP_TO_ALBUM = 119;
    /**
     * 跳转系统相机回调码
     */
    int JUMP_TO_CAMERA = 120;
    /**
     * 跳转系统图库
     */
    int JUMP_TO_VIDEO = 121;

  }

  interface UI {
    /**
     * 隐藏语音未读图片
     */
    int HIDE_POINT = 200;

    /**
     * videoPhone刷新进度条
     */
    int REFRESH_PROGRESSBAR = 201;

    /**
     * 编辑框改变
     */
    int EDITTEXT_HAS_CHANGE = 202;

    /**
     * 刷新界面
     */
    int REFRESH_UI = 203;

    /**
     * 刷新倒计时
     */
    int UPDATE_COUNTDOWN_TIME = 204;

    /**
     * 替换界面
     */
    int REPLACE_FRAGMENT = 205;
  }
}
