package com.bangxuan.xxw.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 产品类型
 */
@Data
@ApiModel(value = "com.bangxuan.xxw.entity.ProductClass",description = "产品类型")
public class ProductClass {


    @ApiModelProperty(value = "类型编号")
    private Integer id;

    @ApiModelProperty(value = "父类型编号")
    private Integer pid;

    @ApiModelProperty(value = "类型名称")
    private String class_name;

    @ApiModelProperty(value = "类型名称英文名称")
    private String class_en;

    @ApiModelProperty(value = "类型级别")
    private Byte level;

    @ApiModelProperty(value = "路由路径")
    private String route_path;

    @ApiModelProperty(value = "排序")
    private Integer class_index;

    @ApiModelProperty(value = "logo")
    private String logo;

    @ApiModelProperty(value = "2d文件")
    private String file2d;

    @ApiModelProperty(value = "类型描述")
    private String class_desc;

    @ApiModelProperty(value = "行业1")
    private String industry;

    @ApiModelProperty(value = "归属目录")
    private String pids;

    @ApiModelProperty(value = "产品数量")
    private Integer pcount;

    @ApiModelProperty(value = "5级类目")
    private Integer clcount;

    @ApiModelProperty(value = "企业编号")
    private Integer company_id;

    @ApiModelProperty(value = "品牌编号")
    private Integer brand_id;

    @ApiModelProperty(value = "推荐")
    private String pc_position;

    @ApiModelProperty(value = "parent_ids")
    private  String parent_ids;

    public String getParent_ids() {
        return this.parent_ids;
    }

    public void setParent_ids(String parent_ids) {
        StringBuffer sb = new StringBuffer();
        String str[]= parent_ids.split(",");
        for (int i=0;i<str.length;i++){
            if(i>=2&&i<str.length-1){
                sb.append(str[i]+"/");
            }else if(i==str.length-1){
                sb.append(str[i]);
            }
        }
        this.parent_ids = sb.toString();
    }
}