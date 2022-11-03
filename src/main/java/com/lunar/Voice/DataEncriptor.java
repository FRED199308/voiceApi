/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunar.Voice;


import org.springframework.stereotype.Service;

/**
 *
 * @author FRED
 */
@Service
public class DataEncriptor {
   
 
    private  int adder []={1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,0,0};
  
    public  String encript(String name)
    {String result="";
    char ch;
    int ck=0;
        int length=name.length();
        for(int i=0;i<length;++i)
        {
            if(ck>=adder.length-1)
            { ck=0;
                
            }
               
            ch=name.charAt(i);
            ch+=adder[ck];
            result+=ch;
            ck++;
            
        }
       return result; 
    }
    public  String decriptor(String name)
    {String result="";
    char ch;
    int ck=0;
        int length=name.length();
        for(int i=0;i<length;++i)
        {
            if(ck>=adder.length-1) ck=0;
            ch=name.charAt(i);
            ch-=adder[ck];
            result+=ch;
            ck++;
            
        }
       return result; 
        
       
    }
   

}
