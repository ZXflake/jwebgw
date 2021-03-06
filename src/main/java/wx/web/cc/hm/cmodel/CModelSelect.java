package wx.web.cc.hm.cmodel;

import configuration.DBO;
import java.util.List;
import plugins.easyui.EasyuiService;
import plugins.easyui.vo.EasyuiPage;
import system.web.JWeb;
import system.base.annotation.H;
import system.base.annotation.M;
import system.base.beanjson.JCJSON;
import system.web.power.ann.SQ;
import wx.web.cc.bean.CModel;
import wx.web.cc.bean.Mybean;
import wx.web.cc.bean.Mybeanfield;
import wx.web.cc.service.MybeanService;
@SQ("Y101_2_1")
@H("cc/cmodal/s")
public class CModelSelect {

    @M("/selectOne")
    public static void selectOne(JWeb jw) {
        String cmodel_zj = jw.getString("cmodel_zj");
        if (null == cmodel_zj || cmodel_zj.isEmpty()) {
            jw.printOne("{}");
            return;
        }
        CModel obj = DBO.service.S.selectOneByID(CModel.class, cmodel_zj);
        obj.setCmodel_nr(obj.getCmodel_nr().replace("'", "&#39;").replace("\r", "&#39;n"));
        jw.printOne(JCJSON.toSimpleJSON(obj));
    }

    @M("/selectOne2")//用于维护界面，点击时，出现的行的模板的具体数据
    public static void selectOne2(JWeb jw) {
        String cmodel_zj = jw.getString("cmodel_zj");
        if (null == cmodel_zj || cmodel_zj.isEmpty()) {
            jw.printOne("{}");
            return;
        }
        CModel obj = DBO.service.S.selectOneByID(CModel.class, cmodel_zj);
        obj.setCmodel_nr(obj.getCmodel_nr().replace("'", "&#39;").replace("\r", "&#39;n"));
        jw.printOne(obj.getCmodel_nr());
    }

    @M("/selectOne3")//用于维护界面，点击时，出现的行的模板的具体数据
    public static void selectOne3(JWeb jw) {
        String cmodel_zj = jw.getString("cmodel_zj");
        String mybean_zj = jw.getString("mybean_zj");
        
        if (null == cmodel_zj || cmodel_zj.isEmpty()||null == mybean_zj || mybean_zj.isEmpty()) {
            jw.printOne("{}");
            return;
        }
        //bean相关信息
        Mybean bean=DBO.service.S.selectOneByID(Mybean.class, mybean_zj);
        //bean属性相关信息
        List<Mybeanfield> fields=DBO.service.S.selectByCondition(Mybeanfield.class, "WHERE mybean_zj='"+mybean_zj+"'");
        //找到具体的模板
        CModel obj = DBO.service.S.selectOneByID(CModel.class, cmodel_zj);
        //执行模板数据绑定
        jw.printOne(MybeanService.myVelocityEngine(obj.getCmodel_nr(), fields, bean));
    }

    
    @M("/selectAllByJson")
    public static void selectJSON(JWeb jw) {

        EasyuiPage page = EasyuiService.getPage(jw);
        List<CModel> list = DBO.service.S.selectVast(CModel.class, page.page, page.rows);
        for (CModel obj : list) {
            obj.setCmodel_nr("");
        }
        jw.printOne(EasyuiService.formatGrid(list));
    }

    @M("/selectAllByJson2")//用作tree 生成select
    public static void selectJSON2(JWeb jw) {
        List<CModel> list = DBO.service.S.select(CModel.class);
        for (CModel obj : list) {
            obj.setCmodel_nr("");
        }
        jw.printOne(JCJSON.toSimpleJSON(list));
    }

    @M("/base/selectVast") // cc/mybean/modal/s/selectVast.jw
    public static void select(JWeb jw) {
        String mybean_zj = jw.getString("mybean_zj");
        if (null == mybean_zj || mybean_zj.isEmpty()) {
            jw.printOne("[]");
            return;
        }
        Mybean bean = DBO.service.S.selectOneByID(Mybean.class, mybean_zj);
        if (null == bean.getMybean_zj()) {
            jw.printOne("[]");
            return;
        }

        String sort = jw.getString("sort", "bean");
        String where = "WHERE mybean_zj='" + mybean_zj + "'";
        List<Mybeanfield> listBF = DBO.service.S.selectByCondition(Mybeanfield.class, where);
        switch (sort) {
            case "bean": {
                jw.printOne(MybeanService.toBeanData(listBF, bean.getMybean_mc()));
                return;
            }
            case "sql": {
                jw.printOne(MybeanService.toSQLData(listBF, bean.getMybean_mc()));
                return;
            }
            case "jsgetset": {
                String js = MybeanService.toJSGet(listBF, bean.getMybean_mc());
                js = js + "\n\n" + MybeanService.toJSSet(listBF, bean.getMybean_mc());
                jw.printOne(js);
            }
        }

    }

}
