/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lxl
 */
public class PageFeatureInfo {
    public int id = 0;
    public int mode = 0;
    public String featrue="";
    public String name = "";
    public int stat = 0;                    //0:disp;1:insert;2:update;3:delete
    public List<PageFeatureField> fields = new ArrayList<PageFeatureField>();
}
