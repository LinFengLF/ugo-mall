<template>
<uni-shadow-root class="vant-button-index"><button :id="id" :class="'custom-class '+(utils.bem('button', [type, size, { block, round, plain, square, loading, disabled, hairline, unclickable: disabled || loading }]))+' '+(hairline ? 'van-hairline--surround' : '')" :open-type="openType" hover-class="van-button--active hover-class" :lang="lang" :business-id="businessId" :session-from="sessionFrom" :send-message-title="sendMessageTitle" :send-message-path="sendMessagePath" :send-message-img="sendMessageImg" :show-message-card="showMessageCard" :app-parameter="appParameter" :aria-label="ariaLabel" @click="onClick" @getuserinfo="bindGetUserInfo" @contact="bindContact" @getphonenumber="bindGetPhoneNumber" @error="bindError" @launchapp="bindLaunchApp" @opensetting="bindOpenSetting">
  <block v-if="loading">
    <van-loading custom-class="loading-class" :size="loadingSize" :color="type === 'default' ? '#c9c9c9' : ''"></van-loading>
    <view v-if="loadingText" class="van-button__loading-text">
      {{ loadingText }}
    </view>
  </block>
  <block v-else>
    <van-icon v-if="icon" size="1.2em" :name="icon" class="van-button__icon" custom-style="line-height: inherit;"></van-icon>
    <view class="van-button__text">
      <slot></slot>
    </view>
  </block>
</button></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanIcon from '../icon/index.vue'
import VanLoading from '../loading/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-loading': VanLoading}}

global['__wxRoute'] = 'vant/button/index'
import { VantComponent } from '../common/component';
import { button } from '../mixins/button';
import { openType } from '../mixins/open-type';
VantComponent({
    mixins: [button, openType],
    classes: ['hover-class', 'loading-class'],
    props: {
        icon: String,
        plain: Boolean,
        block: Boolean,
        round: Boolean,
        square: Boolean,
        loading: Boolean,
        hairline: Boolean,
        disabled: Boolean,
        loadingText: String,
        type: {
            type: String,
            value: 'default'
        },
        size: {
            type: String,
            value: 'normal'
        },
        loadingSize: {
            type: String,
            value: '20px'
        }
    },
    methods: {
        onClick() {
            if (!this.data.disabled && !this.data.loading) {
                this.$emit('click');
            }
        }
    }
});
export default global['__wxComponents']['vant/button/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-button{position:relative;display:inline-block;height:44px;padding:0;font-size:16px;line-height:42px;text-align:center;vertical-align:middle;box-sizing:border-box;border-radius:2px;-webkit-appearance:none;-webkit-text-size-adjust:100%}.van-button:before{position:absolute;top:50%;left:50%;width:100%;height:100%;background-color:#000;border:inherit;border-color:#000;border-radius:inherit;content:" ";opacity:0;-webkit-transform:translate(-50%,-50%);transform:translate(-50%,-50%)}.van-button:after{border-width:0}.van-button--active:before{opacity:.15}.van-button--unclickable:after{display:none}.van-button--default{color:#333;background-color:#fff;border:1px solid #eee}.van-button--primary{color:#fff;background-color:#07c160;border:1px solid #07c160}.van-button--info{color:#fff;background-color:#1989fa;border:1px solid #1989fa}.van-button--danger{color:#fff;background-color:#f44;border:1px solid #f44}.van-button--warning{color:#fff;background-color:#ff976a;border:1px solid #ff976a}.van-button--plain{background-color:#fff}.van-button--plain.van-button--primary{color:#07c160}.van-button--plain.van-button--info{color:#1989fa}.van-button--plain.van-button--danger{color:#f44}.van-button--plain.van-button--warning{color:#ff976a}.van-button--large{width:100%;height:50px;line-height:48px}.van-button--normal{padding:0 15px;font-size:14px}.van-button--small{height:30px;min-width:60px;padding:0 8px;font-size:12px;line-height:28px}.van-button--mini{display:inline-block;width:50px;height:22px;font-size:10px;line-height:20px}.van-button--mini+.van-button--mini{margin-left:5px}.van-button--block{display:block;width:100%}.van-button--round{border-radius:10em}.van-button--square{border-radius:0}.van-button--disabled{opacity:.5}.van-button__text{display:inline}.van-button__loading-text{margin-left:5px;display:inline-block;vertical-align:middle}.van-button__icon{min-width:1em;line-height:inherit!important;vertical-align:top}.van-button__icon+.van-button__text:not(:empty){display:inline-block;margin-left:5px;vertical-align:top}.van-button--hairline{border-width:0;padding-top:1px}.van-button--hairline:after{border-width:1px;border-color:inherit;border-radius:4px}.van-button--hairline.van-button--round:after{border-radius:10em}.van-button--hairline.van-button--square:after{border-radius:0}
</style>