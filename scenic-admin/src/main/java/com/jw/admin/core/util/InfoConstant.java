package com.jw.admin.core.util;

public class InfoConstant {

    public static final int BATCH_SIZE = 5000;

    public static final String[] exportHeaders = {
            "订单号",
            "票种名称",
            "票品名称",
            "景区名称",
            "使用人",
            "使用人手机",
            "游玩时间",
            "核销时间",
            "状态"
    };

    public static final String[] exportFieldCodes = {
            "orderNo",
            "typeName",
            "ticketName",
            "scenicName",
            "useName",
            "usePhone",
            "date",
            "checkTime",
            "status"
    };

    public static final String[] exportProductHeaders = {
            "订单号",
            "凭证号",
            "产品名",
            "下单时间",
            "开始使用时间",
            "使用完成时间",
            "付款方式",
            "支付金额",
            "结算金额",
            "票数",
            "核销数",
            "状态"
    };

    public static final String[] exportProductFieldCodes = {
            "orderNo",
            "certificateNo",
            "productName",
            "createTime",
            "startCheckTime",
            "endCheckTime",
            "orderSource",
            "payMoney",
            "sumMoney",
            "num",
            "useNum",
            "status"
    };

    public static final String[] exportScenicHeaders = {
            "订单号",
            "凭证号",
            "票类",
            "下单时间",
            "游玩时间",
            "来源",
            "订单金额",
            "用户名称",
            "身份证",
            "手机号",
            "票数",
            "核销数",
            "状态"
    };

    public static final String[] exportScenicFieldCodes = {
            "orderNo",
            "certificateNo",
            "ticketName",
            "createTime",
            "firstTime",
            "orderSource",
            "orderMoney",
            "useName",
            "useCardNumber",
            "userPhone",
            "num",
            "useNum",
            "status"
    };

}
