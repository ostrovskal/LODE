package ru.ostrovskal.lode;

public final class Constants
{
    // Базовые
    public static final String SYSTEM_DEFAULT   = "classic";
    public final static int    BLOCK_MINIATURE     = 8;
    public final static int    LIMIT_RECORDS       = 50;

    // ключи установок
    public final static String KEY_SCALE        = "scale";
    public final static String KEY_SPEED        = "speed";
    public final static String KEY_MUSIC        = "music";
    public final static String KEY_SOUND        = "sound";
    public final static String KEY_MASTER       = "master";
    public final static String KEY_AUTHOR       = "author";
    public final static String KEY_AUTHOR_COUNT = "#author_count";
    public final static String KEY_SCALE_VOLUME = "scale_volume";
    public final static String KEY_SPEED_VOLUME = "speed_volume";
    public final static String KEY_MUSIC_VOLUME = "music_volume";
    public final static String KEY_SOUND_VOLUME = "sound_volume";
    public final static String KEY_THEME        = "theme";
    public final static String KEY_TMP_THEME    = "#tmp_theme";
    public final static String KEY_PLAYER       = "player";
    public final static String KEY_SYSTEM       = "#system";
    public final static String KEY_FIRST_START  = "first_start";
    public final static String KEY_EDIT_TILE    = "#edit_tile";
    public final static String KEY_EDIT_LEVEL   = "#edit_level";
    public final static String KEY_EDIT_PREVIEW = "#edit_preview";

    // Типы объектов
    public final static int O_UNDEF             = 0;
    public final static int O_NULL              = 1;
    public final static int O_PERSON            = 2;
    public final static int O_ENEMY1            = 3;
    public final static int O_ENEMY2            = 4;
    public final static int O_BUTTON            = 5;
    public final static int O_POLZ_VERT         = 6;
    public final static int O_POLZ_HORZ         = 7;
    public final static int O_PLATFORM_VERT     = 8;
    public final static int O_PLATFORM_HORZ     = 9;
    public final static int O_FIRE              = 10;
    public final static int O_BUBBLE            = 11;
    public final static int O_ZARAST            = 12;
    public final static int O_DESTR             = 13;

    // Типы тайлов
    public final static byte T_NULL             = 0;
    public final static byte T_BETON            = 1;
    public final static byte T_BRICK            = 2;
    public final static byte T_TRAP             = 3;
    public final static byte T_TRAP_N           = 4;
    public final static byte T_GOLD             = 5;
    public final static byte T_LIMIT            = 6;
    public final static byte T_BUTTON0          = 7;
    public final static byte T_PLATFORM_H       = 8;
    public final static byte T_PLATFORM_V       = 9;
    public final static byte T_POLZ_H_BEGIN     = 10;
    public final static byte T_POLZ_H_MIDDLE    = 11;
    public final static byte T_POLZ_H_END       = 12;
    public final static byte T_POLZ_V_BEGIN     = 13;
    public final static byte T_POLZ_V_MIDDLE    = 14;
    public final static byte T_POLZ_V_END       = 15;
    public final static byte T_FIRE0            = 16;
    public final static byte T_FIRE1            = 17;
    public final static byte T_FIRE2            = 18;
    public final static byte T_FIRE3            = 19;
    public final static byte T_ZARAST0          = 20;
    public final static byte T_ZARAST1          = 21;
    public final static byte T_ZARAST2          = 22;
    public final static byte T_ZARAST3          = 23;
    public final static byte T_DESTR0           = 24;
    public final static byte T_DESTR1           = 25;
    public final static byte T_DESTR2           = 26;
    public final static byte T_DESTR3           = 27;
    public final static byte T_BUTTON1          = 28;
    public final static byte T_BRICK_NULL       = 29;
    public final static byte T_PERSON_DROP      = 29;
    public final static byte T_PERSON_RUN_L0    = 30;
    public final static byte T_PERSON_RUN_L1    = 31;
    public final static byte T_PERSON_RUN_L2    = 32;
    public final static byte T_PERSON_RUN_L3    = 33;
    public final static byte T_PERSON_RUN_R0    = 34;
    public final static byte T_PERSON_RUN_R1    = 35;
    public final static byte T_PERSON_RUN_R2    = 36;
    public final static byte T_PERSON_RUN_R3    = 37;
    public final static byte T_PERSON_TRAP0     = 38;
    public final static byte T_PERSON_TRAP1     = 39;
    public final static byte T_PERSON_TRAP2     = 40;
    public final static byte T_PERSON_TRAP3     = 41;
    public final static byte T_ENEMY1_DROP      = 42;
    public final static byte T_ENEMY1_RUN_L0    = 43;
    public final static byte T_ENEMY1_RUN_L1    = 44;
    public final static byte T_ENEMY1_RUN_L2    = 45;
    public final static byte T_ENEMY1_RUN_L3    = 46;
    public final static byte T_ENEMY1_RUN_R0    = 47;
    public final static byte T_ENEMY1_RUN_R1    = 48;
    public final static byte T_ENEMY1_RUN_R2    = 49;
    public final static byte T_ENEMY1_RUN_R3    = 50;
    public final static byte T_ENEMY1_TRAP0     = 51;
    public final static byte T_ENEMY1_TRAP1     = 52;
    public final static byte T_ENEMY1_TRAP2     = 53;
    public final static byte T_ENEMY1_TRAP3     = 54;
    public final static byte T_ENEMY2_DROP      = 55;
    public final static byte T_ENEMY2_RUN_L0    = 56;
    public final static byte T_ENEMY2_RUN_L1    = 57;
    public final static byte T_ENEMY2_RUN_L2    = 58;
    public final static byte T_ENEMY2_RUN_L3    = 59;
    public final static byte T_ENEMY2_RUN_R0    = 60;
    public final static byte T_ENEMY2_RUN_R1    = 61;
    public final static byte T_ENEMY2_RUN_R2    = 62;
    public final static byte T_ENEMY2_RUN_R3    = 63;
    public final static byte T_ENEMY2_TRAP0     = 64;
    public final static byte T_ENEMY2_TRAP1     = 65;
    public final static byte T_ENEMY2_TRAP2     = 66;
    public final static byte T_ENEMY2_TRAP3     = 67;
    public final static byte T_BUBBLE0          = 68;
    public final static byte T_BUBBLE1          = 69;
    public final static byte T_BUBBLE2          = 70;
    public final static byte T_BUBBLE3          = 71;

    // Маски тайлов
    public final static int MSKO                = 0x1f;

    // Флаги тайлов
    public final static int FA                  = 0x020;// Все гибнут
    public final static int FP                  = 0x040;// Человек гибнет
    public final static int FD                  = 0x080;// Все падают
    public final static int FN                  = 0x100;// Проходимый
    public final static int FT                  = 0x200;// Движение вверх-вниз
    public final static int FL                  = 0x400;// Переменная длина
    //public final static int F_                  = 0x400;

    // Массив перекодировки номеров тайлов в символы при сохранении и наоборот
    public final static char charsOfLevelMap[]  = {' ', 'B'};

    // Массив перекодировки тайлов
    public final static byte remapTiles[]       = {T_NULL, T_BETON, T_BRICK, T_TRAP, T_NULL, T_GOLD, T_LIMIT, T_BRICK, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL,
                                                   T_NULL, T_NULL, T_NULL, T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3, T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3,
                                                   T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3, T_BUTTON1, T_BRICK, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL,
                                                   T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL,
                                                   T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL,
                                                   T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL};

    // Массив характеристик каждого тайла
    public final static int remapProp[]         = {// T_NULL, T_BETON, T_BRICK, T_TRAP, T_TRAP_N
                                                   O_NULL | FD | FA, O_NULL | FA | FP, O_NULL | FA | FP, O_NULL | FT | FN, O_NULL | FD | FN,
                                                   // T_GOLD, T_LIMIT, T_BUTTON0
                                                   O_NULL | FN | FD, O_NULL, O_BUTTON,
                                                   // T_PLATFORM_H, T_PLATFORM_V
                                                   O_PLATFORM_HORZ | FL, O_PLATFORM_VERT | FL,
                                                   // T_POLZ_H_BEGIN, T_POLZ_H_MIDDLE, T_POLZ_H_END, T_POLZ_V_BEGIN, T_POLZ_V_MIDDLE, T_POLZ_V_END
                                                   O_POLZ_HORZ, O_POLZ_HORZ | FL, O_POLZ_HORZ, O_POLZ_VERT, O_POLZ_VERT | FL, O_POLZ_VERT,
                                                   // T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3
                                                   O_FIRE | FN | FA | FD | FP | FL, O_FIRE | FN | FA | FD | FP | FL,
                                                   O_FIRE | FN | FA | FD | FP | FL, O_FIRE | FN | FA | FD | FP | FL,
                                                   // T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3
                                                   O_ZARAST | FN | FD, O_ZARAST | FN | FD, O_ZARAST | FN | FD, O_ZARAST | FN | FD,
                                                   // T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3
                                                   O_DESTR | FN | FD, O_DESTR | FN | FD, O_DESTR | FN | FD, O_DESTR | FN | FD,
                                                   // T_BUTTON1, T_BRICK_NULL
                                                   O_BUTTON, O_NULL | FN | FD,
                                                   // T_PERSON_DROP, T_PERSON_RUN_L0, T_PERSON_RUN_L1, T_PERSON_RUN_L2, T_PERSON_RUN_L3
                                                   O_NULL | FN | FD, O_NULL | FN | FD, O_NULL | FN | FD, O_NULL | FN | FD, O_NULL | FN | FD,
                                                   // T_PERSON_RUN_R0, T_PERSON_RUN_R1, T_PERSON_RUN_R2, T_PERSON_RUN_R3
                                                   O_NULL | FN | FD, O_NULL | FN | FD, O_NULL | FN | FD, O_NULL | FN | FD,
                                                   // T_PERSON_TRAP0, T_PERSON_TRAP1, T_PERSON_TRAP2, T_PERSON_TRAP3
                                                   O_NULL | FN | FD, O_NULL | FN | FD, O_NULL | FN | FD, O_NULL | FN | FD,
                                                   // T_ENEMY1_DROP, T_ENEMY1_RUN_L0, T_ENEMY1_RUN_L1, T_ENEMY1_RUN_L2, T_ENEMY1_RUN_L3
                                                   O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN,
                                                   // T_ENEMY1_RUN_R0, T_ENEMY1_RUN_R1, T_ENEMY1_RUN_R2, T_ENEMY1_RUN_R3
                                                   O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN,
                                                   // T_ENEMY1_TRAP0, T_ENEMY1_TRAP1, T_ENEMY1_TRAP2, T_ENEMY1_TRAP3
                                                   O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN, O_ENEMY1 | FP | FN,
                                                   // T_ENEMY2_DROP, T_ENEMY2_RUN_L0, T_ENEMY2_RUN_L1, T_ENEMY2_RUN_L2, T_ENEMY2_RUN_L3
                                                   O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN,
                                                   // T_ENEMY2_RUN_R0, T_ENEMY2_RUN_R1, T_ENEMY2_RUN_R2, T_ENEMY2_RUN_R3
                                                   O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN,
                                                   // T_ENEMY2_TRAP0, T_ENEMY2_TRAP1, T_ENEMY2_TRAP2, T_ENEMY2_TRAP3
                                                   O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN, O_ENEMY2 | FP | FN,
                                                   // T_BUBBLE0, T_BUBBLE1, T_BUBBLE2, T_BUBBLE3
                                                   O_BUBBLE | FA | FP | FD | FN, O_BUBBLE | FA | FP | FD | FN, O_BUBBLE | FA | FP | FD | FN,
                                                   O_BUBBLE | FA | FP | FD | FN};
}
