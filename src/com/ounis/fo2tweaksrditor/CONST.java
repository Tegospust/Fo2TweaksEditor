/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounis.fo2tweaksrditor;

import javax.security.auth.callback.ConfirmationCallback;

/**
 *
 * @author AndroidDev
 */
public class CONST {
    
    public static final String FILE_FO2TWEAKS = "";//"fo2tweaks.ini"; 
    
    public static final String REM_CHAR = ";";
    public static final String KEY_VAL_SEP = "=";
    public static final String DEF_SPEC_LINE_VAL_SEP = " - ";
    private static final String STR_PAR_LINE_NUM = "%d";
    private static final String STR_PAR_SECTION = "%s";
    private static final String STR_PAR_KEY_VAL = "%s";
    public static final String STR_NUMBER_PREFIX = "#";
    public static final String DEF_SPEC_LINE = STR_NUMBER_PREFIX.concat(STR_PAR_LINE_NUM).concat(DEF_SPEC_LINE_VAL_SEP).
            concat(STR_PAR_SECTION).concat(DEF_SPEC_LINE_VAL_SEP).concat(STR_PAR_KEY_VAL);
    public static final String _DEF_SPEC_LINE = STR_NUMBER_PREFIX + STR_PAR_LINE_NUM + DEF_SPEC_LINE_VAL_SEP + STR_PAR_SECTION 
            + DEF_SPEC_LINE_VAL_SEP + STR_PAR_KEY_VAL;
    
    public static final int CONFIRM_YES = ConfirmationCallback.YES;
    public static final int COMFIRM_NO = ConfirmationCallback.NO;
    public static final String BACKUP_FILE_SUFF = "_kopia";
    
}
