/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import java.io.IOException;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;

/**
 *
 * @author kedar
 */
public class SPlainSelect extends AbsClass
{
    AbsClass result;
    
    SPlainSelect(SelectBody selectB, Tabless table)throws IOException
    {
        PlainSelect plainS = (PlainSelect) selectB;
        FromItem fromI = plainS.getFromItem();
        result = new SFromItem(fromI, table);
        
        if(plainS.getJoins() != null)
        {
            for(Join j : plainS.getJoins())
            {
                AbsClass temp = new SFromItem(j.getRightItem(), table);
                //result = new SJoins(result, temp);
            }
        }
        
        if(plainS.getWhere() != null)
            result = new SWhere(result, table, plainS); 
        result = new SSelectItem(result, table, plainS);
    }
    
    @Override
    public boolean isNext()
    {
         boolean var;
        var = (!result.isNext())?(false):(true);
        return var; //return true;
    }
    
    @Override
    public void clear()
    {
        result.clear();
    }
    
    @Override
    public TableSpecifics get()
    {
        return result.get();
    }
}
