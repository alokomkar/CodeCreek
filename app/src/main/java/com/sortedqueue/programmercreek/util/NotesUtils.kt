package com.sortedqueue.programmercreek.util

import com.sortedqueue.programmercreek.database.NotesModel

import java.util.ArrayList
import java.util.Arrays

/**
 * Created by Alok Omkar on 2017-07-28.
 */

object NotesUtils {

    val ALL_KEYWORDS = "break,continue,do,else,for,if,return,while,auto,case,char,const,default,double,enum,extern,float,goto,inline,int,long,register,short,signed,sizeof,static,struct,switch,typedef,union,unsigned,void,volatile,catch,class,delete,false,import,new,operator,private,protected,public,this,throw,true,try,typeof,alignof,align_union,asm,axiom,bool,concept,concept_map,const_cast,constexpr,decltype,delegate,dynamic_cast,explicit,export,friend,generic,late_check,mutable,namespace,nullptr,property,reinterpret_cast,static_assert,static_cast,template,typeid,typename,using,virtual,where,break,continue,do,else,for,if,return,while,auto,case,char,const,default,double,enum,extern,float,goto,inline,int,long,register,short,signed,sizeof,static,struct,switch,typedef,union,unsigned,void,volatile,catch,class,delete,false,import,new,operator,private,protected,public,this,throw,true,try,typeof,abstract,assert,boolean,byte,extends,final,finally,implements,import,instanceof,interface,null,native,package,strictfp,super,synchronized,throws,transient,as,base,by,checked,decimal,delegate,descending,dynamic,event,fixed,foreach,from,group,implicit,in,internal,into,is,let,lock,object,out,override,orderby,params,partial,readonly,ref,sbyte,sealed,stackalloc,string,select,uint,ulong,unchecked,unsafe,ushort,var,virtual,where,break,continue,do,else,for,if,return,while,auto,case,char,const,default,double,enum,extern,float,goto,inline,int,long,register,short,signed,sizeof,static,struct,switch,typedef,union,unsigned,void,volatile,catch,class,delete,false,import,new,operator,private,protected,public,this,throw,true,try,typeof,debugger,eval,export,function,get,null,set,undefined,var,with,Infinity,NaN,caller,delete,die,do,dump,elsif,eval,exit,foreach,for,goto,if,import,last,local,my,next,no,our,print,package,redo,require,sub,undef,unless,until,use,wantarray,while,BEGIN,END,break,continue,do,else,for,if,return,while,and,as,assert,class,def,del,elif,except,exec,finally,from,global,import,in,is,lambda,nonlocal,not,or,pass,print,raise,try,with,yield,False,True,None,break,continue,do,else,for,if,return,while,alias,and,begin,case,class,def,defined,elsif,end,ensure,false,in,module,next,nil,not,or,redo,rescue,retry,self,super,then,true,undef,unless,until,when,yield,BEGIN,END,break,continue,do,else,for,if,return,while,case,done,elif,esac,eval,fi,function,in,local,set,then,until"

    var keywords = ALL_KEYWORDS.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    var stringList: List<String> = ArrayList(Arrays.asList(*keywords))

    fun isCode(noteLine: String): Boolean {

        if (noteLine.startsWith("#") || noteLine.endsWith(";") || noteLine.contains("{") || noteLine.contains("}")) {
            return true
        }
        val words = noteLine.replace(";".toRegex(), "").split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var count = 0
        for (word in words) {
            if (stringList.contains(word)) count++
        }
        if (count > 1) {
            return true
        }
        return false
    }

    fun splitParaIntoNotes(programCode: String): ArrayList<NotesModel> {
        val lines = programCode.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val notesModelArrayList = ArrayList<NotesModel>()
        var index = 0
        for (line in lines) {
            if (!line.trim { it <= ' ' }.isEmpty()) {

                val isCode = isCode(line)
                if (index == 0 && !isCode)
                    notesModelArrayList.add(NotesModel(line, NotesModel.TYPE_HEADER))
                else if (isCode)
                    notesModelArrayList.add(NotesModel(line, NotesModel.TYPE_CODE))
                else
                    notesModelArrayList.add(NotesModel(line, NotesModel.TYPE_NOTES))



                index++
            }
        }
        return notesModelArrayList
    }

}
