package com.jw.admin.modular.scenic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jw.admin.core.controller.BaseController;
import com.jw.admin.core.shiro.ShiroKit;
import com.jw.admin.core.tips.SuccessTip;
import com.jw.admin.core.tips.Tip;
import com.jw.common.support.IdUtils;
import com.jw.sys.api.IScenicSpotService;
import com.jw.sys.api.ITFileService;
import com.jw.sys.bean.vo.ScenicSpotVo;
import com.jw.sys.bean.vo.TFileVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/scenicSpot")
public class ScenicSpotController extends BaseController {

    private static String PREFIX = "/modular/scenic/scenic_info/";

    @Autowired
    IScenicSpotService scenicSpotService;

    @Autowired
    ITFileService fileService;

    @Value("${fileurl.uploadurl}")
    private String filePath;

    @Value("${fileurl.videoUploadUrl}")
    private String videoPath;

    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("user",ShiroKit.getUser());
        return PREFIX + "scenicSpot_index.html";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) int offset,
            @RequestParam(required = false) int limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("name : " + name);
        try {
            //2:分页查询
            Map<String, Object> dataMap = new HashMap<>();
            if (name != null && !"".equals(name)) {
                dataMap.put("name", name);
            } else {
                dataMap.put("name", "");
            }
            dataMap.put("offset", offset);
            dataMap.put("limit", limit);
            List<ScenicSpotVo> list = null;
            //1:统计条数
            if(ShiroKit.getUser().getScenicId() == 0) {
                long total = scenicSpotService.selectAllCount(dataMap);
                list = scenicSpotService.selectInfoList(dataMap);

                //3:返回Map
                map.put("total", total);
                map.put("flag", false);
                map.put("rows", list);
            } else {
                list = new ArrayList<>();
                list.add(scenicSpotService.selectById(ShiroKit.getUser().getScenicId()));
                map.put("total", 1);
                map.put("flag", false);
                map.put("rows", list);
            }
            System.out.println(JSONObject.toJSONString(list));
            return map;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        return map;
    }

    @RequestMapping("/addScenicSpot")
    public String addScenicSpot(Model model){
        return PREFIX + "scenicSpot_add.html";
    }

    @RequestMapping("/add")
    @ResponseBody
    public Tip add(@Valid ScenicSpotVo scenicSpotVo){
        System.out.println("ScenicSpotVo : " + JSONObject.toJSONString(scenicSpotVo));
        try {
            scenicSpotVo.setCreateTime(new Date());
            scenicSpotVo.setCreator("1");
            scenicSpotVo.setIsDelete("0");
            scenicSpotVo.setCode(IdUtils.createUUID(32));
            int scenicSpotId = scenicSpotService.insert(scenicSpotVo);

            TFileVo fileVo = new TFileVo();
            int len = scenicSpotVo.getFileSize().split(",").length;

            for(int i=0 ;i<len;i++){
                String fileSize = scenicSpotVo.getFileSize().split(",")[i];
                String fileUrl = scenicSpotVo.getFileUrl().split(",")[i];
                String fileName = scenicSpotVo.getFileName().split(",")[i];
                fileVo.setType("1");
                fileVo.setCreator("1");
                fileVo.setUrl(fileUrl);
                fileVo.setSize(getFileSize(fileSize));
                fileVo.setCreateTime(new Date());
                fileVo.setName(fileName);
                fileVo.setLinktId(scenicSpotId);
                fileVo.setIsDelete("0");
                fileService.insert(fileVo);
            }
            return SUCCESS_TIP;
        } catch (Exception e) {
            log.info("",e);
        }
        return ERROR_TIP;
    }

    @RequestMapping("/editScenicSpot/{id}")
    public String editScenicSpot(@PathVariable Integer id, Model model){
        ScenicSpotVo scenicSpotVo = scenicSpotService.selectById(id);
        TFileVo fileVo = new TFileVo();
        fileVo.setLinktId(id);
        fileVo.setType("1");
        List<TFileVo> fileResult = fileService.fileList(fileVo);
        model.addAttribute("ScenicSpotVo",scenicSpotVo);
        model.addAttribute("fileResult",JSON.toJSONString(fileResult));
        System.out.println("ScenicSpotVo : " + JSONObject.toJSONString(scenicSpotVo));
        System.out.println("fileResult : " + JSON.toJSONString(fileResult));
        return PREFIX + "scenicSpot_edit.html";
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Tip edit(@Valid ScenicSpotVo scenicSpotVo){
        log.info("scenicSpotVo : "+ JSON.toJSONString(scenicSpotVo));
        try{
            //String filePath = "/upload/scenic_img_";
            scenicSpotVo.setModifyTime(new Date());
            scenicSpotVo.setMender("1");
            /*String headUrl = scenicSpotVo.getHeadImg();
            String headImg = headUrl.substring(headUrl.indexOf(filePath));
            scenicSpotVo.setHeadImg(headImg);
            System.out.println("图片地址 : " + scenicSpotVo.getHeadImg());*/
            scenicSpotService.update(scenicSpotVo);

            //先删除原来的文件
            /*TFileVo deleteFileVo = new TFileVo();
            deleteFileVo.setType("1");
            deleteFileVo.setLinktId(scenicSpotVo.getId());
            fileService.delete(deleteFileVo);

            TFileVo fileVo = new TFileVo();
            int len = scenicSpotVo.getFileSize().split(",").length;

            for(int i=0 ;i<len;i++){
                String fileSize = scenicSpotVo.getFileSize().split(",")[i];
                String url = scenicSpotVo.getFileUrl().split(",")[i];
                String fileUrl = url.substring(url.indexOf(filePath));
                String fileName = scenicSpotVo.getFileName().split(",")[i];
                fileVo.setType("1");
                fileVo.setCreator("1");
                fileVo.setUrl(fileUrl);
                fileVo.setSize(getFileSize(fileSize));
                fileVo.setCreateTime(new Date());
                fileVo.setName(fileName);
                fileVo.setLinktId(scenicSpotVo.getId());
                fileVo.setIsDelete("0");
                fileService.insert(fileVo);
            }*/

            return SUCCESS_TIP;
        }catch (Exception e){
            log.info("",e);
        }
        return ERROR_TIP;
    }


    /**
     * 删除活动信息
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Tip delete(@Valid Integer id){
        try{
            ScenicSpotVo scenicSpotVo = scenicSpotService.selectById(id);
            scenicSpotVo.setIsDelete("1");
            scenicSpotService.update(scenicSpotVo);
            return SUCCESS_TIP;
        } catch (Exception e) {
            log.info("",e);
        }

        return new SuccessTip(400, "删除失败,请重新操作或联系管理员");
    }

    /**
     * 上传文件至服务器
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadImg")
    @ResponseBody
    public Map<String, String> test(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "100");
        try {
            Part part = request.getPart("file_id");
            String header = part.getHeader("content-disposition").replace("form-data; ", "");
            log.info("BB  " + header);
            log.info(JSON.toJSONString(part));
            log.info("BB  " + header);
            String fileName = part.getSubmittedFileName();
            String endName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String uuid = "";
            for (int i = 0; i < 10; i++) {
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            String folder = "scenic_img_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
            String txtUrl = filePath + folder;
            File file1 = new File(txtUrl);
            if (!file1.exists()) {
                file1.mkdir();
            }
            part.write(filePath + folder + "/" + uuid + endName);
            String md5Str = DigestUtils.md5Hex(new FileInputStream(filePath + folder + "/" + uuid + endName));
            map.put("code", "200");
            map.put("name", fileName);
            map.put("url", "/upload/" + folder + "/" + uuid + endName);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }

    /**
     * 上传文件至服务器
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadVideo")
    @ResponseBody
    public Map<String, String> uploadVideo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "100");
        try {
            request.getParameter("file");
            System.out.println("name : " + request.getPart("file"));
            System.out.println("name1 : " + request.getPart("posterImg"));
            Part part;
            if(request.getPart("file") != null){
                part = request.getPart("file");
            } else {
                part = request.getPart("posterImg");
            }
//            String header = part.getHeader("content-disposition").replace("form-data; ", "");
//            log.info("BB  " + header);
//            log.info(JSON.toJSONString(part));
//            log.info("BB  " + header);
            String fileName = part.getSubmittedFileName();
            String endName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String uuid = "";
            for (int i = 0; i < 10; i++) {
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            String folder = "scenic_video_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
            String txtUrl = videoPath + folder;
            File file1 = new File(txtUrl);
            if (!file1.exists()) {
                System.out.println("新建");
                file1.mkdir();
            }
            part.write(videoPath + folder + "/" + uuid + endName);
//            String md5Str = DigestUtils.md5Hex(new FileInputStream(videoPath + folder + "/" + uuid + endName));
            map.put("code", "200");
            map.put("name", fileName);
            map.put("url", "/upload/video/" + folder + "/" + uuid + endName);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }

    @RequestMapping("/chooseVideo")
    public String chooseVideo(HttpServletRequest request , Model model){
        return PREFIX + "chooseVideo_index.html";
    }

    private Integer getFileSize(String size) {
        Integer b = 0;
        //log.info(size.substring(0, size.indexOf(" ")));
        try {
            if (size.contains("KB")) {
                b = (int) (Math.ceil(1024 * Double.parseDouble(size.substring(0, size.indexOf(" ")))));
            } else if (size.contains("MB")) {
                b = (int) (Math.ceil(1024 * 1024 * Double.parseDouble(size.substring(0, size.indexOf(" ")))));
            } else {
                b = (int) (Math.ceil(Double.parseDouble(size.substring(0, size.indexOf(" ")))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

}
