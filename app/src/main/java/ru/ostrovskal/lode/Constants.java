package ru.ostrovskal.lode;

import android.graphics.Color;
import android.view.Gravity;

import static com.github.ostrovskal.ssh.Constants.SEEK_ANIM_ROTATE;
import static com.github.ostrovskal.ssh.Constants.SEEK_ANIM_SCALE;
import static com.github.ostrovskal.ssh.Constants.TILE_GRAVITY_CENTER;
import static com.github.ostrovskal.ssh.Constants.TILE_SCALE_MIN;
import static com.github.ostrovskal.ssh.Constants.TILE_SHAPE_ROUND;
import static com.github.ostrovskal.ssh.Constants.TILE_STATE_HOVER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_BACKGROUND;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_CLICKABLE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_COLOR_DEFAULT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_DRW;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_FOCUSABLE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_FONT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_GRAVITY;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_INT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_PADDING;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SHADOW_COLOR;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SHADOW_DX;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SHADOW_DY;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SHADOW_RADIUS;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SIZE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_ALIGNED;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BITMAP_NAME;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_BACKGROUND;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_BUTTONS;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_CHECK;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_EDIT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_HEADER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_ICONS;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_MENU;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_RADIO;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_SEEK;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_SELECT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_SWITCH;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_TILES;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_TOOLS;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_DIVIDER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_HEADER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_HINT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_HTML_HEADER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_LARGE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_LAYOUT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_LINK;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_MESSAGE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_NORMAL;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_SELECTOR;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_SMALL;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_WINDOW;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_WIRED;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_GRAVITY;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_HORZ;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_MODE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_RADII;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_SCALE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_SHAPE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_SPACING;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_STATES;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_THEME_NAME;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_VERT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_WIDTH_SELECTOR;
import static com.github.ostrovskal.ssh.StylesAndAttrs.COLOR;
import static com.github.ostrovskal.ssh.StylesAndAttrs.THEME;

public final class Constants
{
    // Базовые
    public static final String SYSTEM_DEFAULT   = "classic";
    public final static int    BLOCK_MINIATURE     = 8;
    public final static int    LIMIT_RECORDS       = 50;
    public final static int    STD_GAME_SPEED      = 50;

    // Действия
    public static final int ACTION_LOAD     = 10000; // загрузка уровня
    public static final int ACTION_SAVE     = 10001; // сохранение уровня
    public static final int ACTION_DELETE   = 10002; // удаление уровня
    public static final int ACTION_NEW      = 10003; // новый уровень
    public static final int ACTION_GENERATE = 10004; // генерация классических уровней
    public static final int ACTION_SYSTEM   = 10005; // новая система
    public static final int ACTION_FINISH   = 10006; // последний уровень системы

    // Состояние игры
    public static final int STATUS_INIT     = 1; // инициализация
    public static final int STATUS_DEAD     = 2; // игрок мертв
    public static final int STATUS_CLEARED  = 3; // уровень пройден
    public static final int STATUS_PREPARED = 4; // установка значений
    public static final int STATUS_LOOP     = 5; // игровой цикл
    public static final int STATUS_MESSAGE  = 6; // сообщение
    public static final int STATUS_EXIT     = 7; // выход
    public static final int STATUS_UNK      = 8; // неопределено
    public static final int STATUS_SUICIDED = 9; // самоубийство

    // формы
    public final static int FORM_GAME           = 0;
    public final static int FORM_EDITOR         = 1;
    public final static int FORM_SPLASH         = 2;
    public final static int FORM_MENU           = 3;
    public final static int FORM_DLG_G_ACTIONS  = 4;
    public final static int FORM_DLG_E_ACTIONS  = 5;


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
    public final static int O_BETON             = 1;
    public final static int O_BRICK             = 2;
    public final static int O_TRAP              = 3;
    public final static int O_GOLD              = 4;
    public final static int O_BUTTON            = 5;
    public final static int O_BRICK_NULL        = 6;
    public final static int O_NULL              = 7;
    public final static int O_PERSON            = 8;
    public final static int O_ENEMY1            = 9;
    public final static int O_ENEMY2            = 10;
    public final static int O_POLZ_VERT         = 11;
    public final static int O_POLZ_HORZ         = 12;
    public final static int O_PLATFORM_VERT     = 13;
    public final static int O_PLATFORM_HORZ     = 14;
    public final static int O_FIRE              = 15;
    public final static int O_BUBBLE            = 16;
    public final static int O_ZARAST            = 17;
    public final static int O_DESTR             = 18;

    // Типы тайлов
    public final static byte T_BETON            = 0;
    public final static byte T_BRICK            = 1;
    public final static byte T_TRAP             = 2;
    public final static byte T_GOLD             = 3;
    public final static byte T_BUTTON1          = 4;
    public final static byte T_FIRE0            = 5;
    public final static byte T_FIRE1            = 6;
    public final static byte T_FIRE2            = 7;
    public final static byte T_FIRE3            = 8;
    public final static byte T_BRICK_NULL       = 9;
    public final static byte T_ZARAST0          = 10;
    public final static byte T_ZARAST1          = 11;
    public final static byte T_ZARAST2          = 12;
    public final static byte T_ZARAST3          = 13;
    public final static byte T_DESTR0           = 14;
    public final static byte T_DESTR1           = 15;
    public final static byte T_DESTR2           = 16;
    public final static byte T_DESTR3           = 17;
    public final static byte T_LIMIT            = 18;
    public final static byte T_BUTTON0          = 19;
    public final static byte T_NULL             = 20;
    public final static byte T_TRAP_N           = 21;
    public final static byte T_PLATFORM_H       = 22;
    public final static byte T_PLATFORM_V       = 23;
    public final static byte T_POLZ_H_BEGIN     = 24;
    public final static byte T_POLZ_H_MIDDLE    = 25;
    public final static byte T_POLZ_H_END       = 26;
    public final static byte T_POLZ_V_BEGIN     = 27;
    public final static byte T_POLZ_V_MIDDLE    = 28;
    public final static byte T_POLZ_V_END       = 29;
    public final static byte T_SCORE            = 30;
    public final static byte T_PERSON_DROP      = 31;
    public final static byte T_PERSON_RUN_L0    = 32;
    public final static byte T_PERSON_RUN_L1    = 33;
    public final static byte T_PERSON_RUN_L2    = 34;
    public final static byte T_PERSON_RUN_L3    = 35;
    public final static byte T_PERSON_RUN_R0    = 36;
    public final static byte T_PERSON_RUN_R1    = 37;
    public final static byte T_PERSON_RUN_R2    = 38;
    public final static byte T_PERSON_RUN_R3    = 39;
    public final static byte T_PERSON_TRAP0     = 40;
    public final static byte T_PERSON_TRAP1     = 41;
    public final static byte T_PERSON_TRAP2     = 42;
    public final static byte T_PERSON_TRAP3     = 43;
    public final static byte T_ENEMY1_DROP      = 44;
    public final static byte T_ENEMY1_RUN_L0    = 45;
    public final static byte T_ENEMY1_RUN_L1    = 46;
    public final static byte T_ENEMY1_RUN_L2    = 47;
    public final static byte T_ENEMY1_RUN_L3    = 48;
    public final static byte T_ENEMY1_RUN_R0    = 49;
    public final static byte T_ENEMY1_RUN_R1    = 50;
    public final static byte T_ENEMY1_RUN_R2    = 51;
    public final static byte T_ENEMY1_RUN_R3    = 52;
    public final static byte T_ENEMY1_TRAP0     = 53;
    public final static byte T_ENEMY1_TRAP1     = 54;
    public final static byte T_ENEMY1_TRAP2     = 55;
    public final static byte T_ENEMY1_TRAP3     = 56;
    public final static byte T_ENEMY2_DROP      = 57;
    public final static byte T_ENEMY2_RUN_L0    = 58;
    public final static byte T_ENEMY2_RUN_L1    = 59;
    public final static byte T_ENEMY2_RUN_L2    = 60;
    public final static byte T_ENEMY2_RUN_L3    = 61;
    public final static byte T_ENEMY2_RUN_R0    = 62;
    public final static byte T_ENEMY2_RUN_R1    = 63;
    public final static byte T_ENEMY2_RUN_R2    = 64;
    public final static byte T_ENEMY2_RUN_R3    = 65;
    public final static byte T_ENEMY2_TRAP0     = 66;
    public final static byte T_ENEMY2_TRAP1     = 67;
    public final static byte T_ENEMY2_TRAP2     = 68;
    public final static byte T_ENEMY2_TRAP3     = 69;
    public final static byte T_BUBBLE0          = 70;
    public final static byte T_BUBBLE1          = 71;
    public final static byte T_BUBBLE2          = 72;
    public final static byte T_BUBBLE3          = 73;

    // Маски тайлов
    public final static int MSKO                = 0x1f;
    public final static int MSKT                = 0x7f;

    // Флаги тайлов
    public final static int FA                  = 0x020;// Все гибнут
    public final static int FP                  = 0x040;// Человек гибнет
    public final static int FD                  = 0x080;// Все падают
    public final static int FN                  = 0x100;// Проходимый
    public final static int FT                  = 0x200;// Движение вверх-вниз
    public final static int FL                  = 0x400;// Переменная длина
    public final static int FE                  = 0x080;// Enemy на чем то

    // Массив перекодировки номеров тайлов в символы при сохранении и наоборот
    public final static char charsOfLevelMap[]  = {// BETON, BRICK, TRAP, GOLD, BUTTON1, FIRE0,,,,BRICK_NULL,,,,,,,,BUTTON0
                                                   'B', 'W', 'H', 'x', '?', 'F', '?', '?', '?', 'N', '?', '?', '?', '?', '?', '?', '?', '?', 'b',
                                                   // BUTTON, NULL, TRAP_N, PLAT_H, PLAT_V,,POLZ_H,,,POLZ_V,
                                                   'w', ' ', 'h', '=', '+', '?', '-', '?', '?', '|', '?', '?',
                                                   // PERSON
                                                   'M', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
                                                   // ENEMY1
                                                   '1', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
                                                   // ENEMY2
                                                   '2', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
                                                   // BUBBLE
                                                   '?', '?', '?', '?'};

    // Массив перекодировки тайлов
    public final static int remapTiles[]        = {T_BETON, T_BRICK, T_TRAP, T_GOLD, T_BUTTON1, T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3, T_BRICK, T_ZARAST0,
                                                   T_ZARAST1, T_ZARAST2, T_ZARAST3, T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3, T_LIMIT, T_BRICK, T_NULL,
                                                   T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_SCORE, T_NULL, T_NULL,
                                                   T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL,
                                                   T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL,
                                                   T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL, T_NULL};

    // Массив характеристик каждого тайла
    public final static int remapProp[]         = {// T_BETON, T_BRICK, T_TRAP, T_GOLD
                                                   O_BETON | FA | FP, O_BRICK | FA | FP, O_TRAP | FT | FN, O_NULL | FD | FN, O_GOLD | FN | FD,
                                                   // T_BUTTON1
                                                   O_BUTTON,
                                                   // T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3
                                                   O_FIRE | FN | FA | FD | FP | FL, O_FIRE | FN | FA | FD | FP | FL,
                                                   O_FIRE | FN | FA | FD | FP | FL, O_FIRE | FN | FA | FD | FP | FL,
                                                   // T_BRICK_NULL
                                                   O_BRICK_NULL | FD | FN,
                                                   // T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3
                                                   O_ZARAST | FN | FD, O_ZARAST | FN | FD, O_ZARAST | FN | FD, O_ZARAST | FN | FD,
                                                   // T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3
                                                   O_DESTR | FN | FD, O_DESTR | FN | FD, O_DESTR | FN | FD, O_DESTR | FN | FD,
                                                   // T_LIMIT, T_BUTTON0, T_NULL, T_TRAP_N
                                                   O_BETON, O_BUTTON, O_NULL | FD | FN, O_TRAP | FD | FN,
                                                   // T_PLATFORM_H, T_PLATFORM_V
                                                   O_PLATFORM_HORZ | FL, O_PLATFORM_VERT | FL,
                                                   // T_POLZ_H_BEGIN, T_POLZ_H_MIDDLE, T_POLZ_H_END, T_POLZ_V_BEGIN, T_POLZ_V_MIDDLE, T_POLZ_V_END
                                                   O_POLZ_HORZ, O_POLZ_HORZ | FL, O_POLZ_HORZ, O_POLZ_VERT, O_POLZ_VERT | FL, O_POLZ_VERT,
                                                   // T_SCORE
                                                   O_NULL | FN | FD,
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

    public final static int tilesGamePanel[]        = {R.integer.TILE_BETON, R.integer.TILE_GOLD, R.integer.TILE_BRICK};

    private static final int ATTR_SSH_COLOR_PANEL_COUNTERS = 1000 | ATTR_INT;
    private static final int ATTR_SSH_COLOR_STAT_COUNTERS  = 1002 | ATTR_INT;
    private static final int ATTR_SSH_BM_PANEL_PORT        = 1003 | ATTR_DRW;
    private static final int ATTR_SSH_BM_PANEL_LAND        = 1004 | ATTR_DRW;

    public static final int[] themeDark = {ATTR_SSH_SPACING, R.dimen.cellSpacing,
                                           ATTR_SSH_THEME_NAME, R.string.themeDark,
                                           ATTR_SSH_BM_MENU, R.drawable.menu,
                                           ATTR_SSH_BM_ICONS, R.drawable.icon_tiles,
                                           ATTR_SSH_MODE, SEEK_ANIM_ROTATE,
                                           ATTR_SSH_COLOR_DIVIDER, 0x7a7a7a | COLOR,
                                           ATTR_SSH_COLOR_LAYOUT, 0x2d2929 | COLOR,
                                           ATTR_SSH_COLOR_NORMAL, 0x9599f7 | COLOR,
                                           ATTR_SSH_COLOR_LARGE, 0xbc5a1d | COLOR,
                                           ATTR_SSH_COLOR_SMALL, 0x2ea362 | COLOR,
                                           ATTR_SSH_COLOR_HINT, 0xf77499 | COLOR,
                                           ATTR_SSH_COLOR_SELECTOR, Color.MAGENTA | COLOR,
                                           ATTR_SSH_COLOR_HEADER, 0xcfba41 | COLOR,
                                           ATTR_SSH_COLOR_HTML_HEADER, 0xf22782 | COLOR,
                                           ATTR_SSH_COLOR_PANEL_COUNTERS, 0xeac8c8 | COLOR,
                                           ATTR_SSH_COLOR_STAT_COUNTERS, 0xaafdfd | COLOR,
                                           ATTR_SSH_COLOR_LINK, 0xb8fa01 | COLOR,
                                           ATTR_SSH_COLOR_MESSAGE, 0xd2fa64 | COLOR,
                                           ATTR_SSH_COLOR_WINDOW, 0x030303 | COLOR,
                                           ATTR_SSH_COLOR_WIRED, 0x808080 | COLOR,
                                           ATTR_SSH_BM_BACKGROUND, R.drawable.theme_background_dark,
                                           ATTR_SSH_BM_HEADER, R.drawable.theme_header_dark,
                                           ATTR_SSH_BM_SELECT, R.drawable.theme_select_dark,
                                           ATTR_SSH_BM_EDIT, R.drawable.theme_edit_dark,
                                           //ATTR_SSH_BM_PANEL_PORT, R.drawable.theme_panel_port_dark,
                                           //ATTR_SSH_BM_PANEL_LAND, R.drawable.theme_panel_land_dark,
                                           ATTR_SSH_BM_TOOLS, R.drawable.theme_tool_dark,
                                           ATTR_SSH_BM_BUTTONS, R.drawable.theme_button_dark,
                                           ATTR_SSH_BM_RADIO, R.drawable.theme_radio_dark,
                                           ATTR_SSH_BM_CHECK, R.drawable.theme_check_dark,
                                           ATTR_SSH_BM_SEEK, R.drawable.theme_seek_dark,
                                           ATTR_SSH_BM_SWITCH, R.drawable.theme_switch_dark,
                                           ATTR_SSH_BM_TILES, R.drawable.sprites};

    public static final int[] themeLight = {ATTR_SSH_SPACING, R.dimen.cellSpacing,
                                            ATTR_SSH_THEME_NAME, R.string.themeLight,
                                            ATTR_SSH_BM_MENU, R.drawable.menu,
                                            ATTR_SSH_BM_ICONS, R.drawable.icon_tiles,
                                            ATTR_SSH_MODE, SEEK_ANIM_SCALE,
                                            ATTR_SSH_COLOR_DIVIDER, 0x7f7f7f | COLOR,
                                            ATTR_SSH_COLOR_LAYOUT, 0x9e5e1e | COLOR,
                                            ATTR_SSH_COLOR_NORMAL, 0xea5191 | COLOR,
                                            ATTR_SSH_COLOR_LARGE, 0x5670f1 | COLOR,
                                            ATTR_SSH_COLOR_SMALL, 0x9841df | COLOR,
                                            ATTR_SSH_COLOR_HINT, 0xf77499 | COLOR,
                                            ATTR_SSH_COLOR_SELECTOR, 0xf23346 | COLOR,
                                            ATTR_SSH_COLOR_HEADER, 0xa9f145 | COLOR,
                                            ATTR_SSH_COLOR_HTML_HEADER, 0x9a4dfc | COLOR,
                                            ATTR_SSH_COLOR_PANEL_COUNTERS, 0xb7a0a0 | COLOR,
                                            ATTR_SSH_COLOR_STAT_COUNTERS, 0xe36efa | COLOR,
                                            ATTR_SSH_COLOR_LINK, 0xb8fa01 | COLOR,
                                            ATTR_SSH_COLOR_MESSAGE, 0xd2fa64 | COLOR,
                                            ATTR_SSH_COLOR_WINDOW, 0x020202 | COLOR,
                                            ATTR_SSH_COLOR_WIRED, 0x404040 | COLOR,
                                            ATTR_SSH_BM_BACKGROUND, R.drawable.theme_background_light,
                                            ATTR_SSH_BM_HEADER, R.drawable.theme_header_light,
                                            ATTR_SSH_BM_SELECT, R.drawable.theme_select_light,
                                            ATTR_SSH_BM_EDIT, R.drawable.theme_edit_light,
                                            //ATTR_SSH_BM_PANEL_PORT, R.drawable.theme_panel_port_light,
                                            //ATTR_SSH_BM_PANEL_LAND, R.drawable.theme_panel_land_light,
                                            ATTR_SSH_BM_TOOLS, R.drawable.theme_tool_light,
                                            ATTR_SSH_BM_BUTTONS, R.drawable.theme_button_light,
                                            ATTR_SSH_BM_RADIO, R.drawable.theme_radio_light,
                                            ATTR_SSH_BM_CHECK, R.drawable.theme_check_light,
                                            ATTR_SSH_BM_SEEK, R.drawable.theme_seek_light,
                                            ATTR_SSH_BM_SWITCH, R.drawable.theme_switch_light,
                                            ATTR_SSH_BM_TILES, R.drawable.sprites};

    public static final int[] style_tile_lode = {ATTR_SSH_VERT, 6,
                                                  ATTR_SSH_HORZ, 10,
                                                  ATTR_FOCUSABLE, 0,
                                                  ATTR_SSH_WIDTH_SELECTOR, 0,
                                                  ATTR_BACKGROUND, ATTR_SSH_COLOR_SELECTOR | THEME,
                                                  ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_TILES | THEME};

    public static final int[] style_button_actions = {ATTR_SHADOW_DX, R.dimen.shadowTextX,
                                                      ATTR_SHADOW_DY, R.dimen.shadowTextY,
                                                      ATTR_SHADOW_RADIUS, R.dimen.shadowTextR,
                                                      ATTR_SHADOW_COLOR, 0x1 | COLOR,
                                                      ATTR_SIZE, R.dimen.large,
                                                      ATTR_FONT, R.string.font_large,
                                                      ATTR_COLOR_DEFAULT, ATTR_SSH_COLOR_MESSAGE | THEME,
                                                      ATTR_CLICKABLE, 1,
                                                      ATTR_PADDING, 2,
                                                      ATTR_SSH_ALIGNED, 1,
                                                      ATTR_SSH_HORZ, 2,
                                                      ATTR_SSH_STATES, TILE_STATE_HOVER,
                                                      ATTR_GRAVITY, Gravity.CENTER,
                                                      ATTR_SSH_GRAVITY, TILE_GRAVITY_CENTER,
                                                      ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_BUTTONS | THEME };

    public static final int[] style_text_finish = {ATTR_SHADOW_DX, R.dimen.shadowTextX,
                                                   ATTR_SHADOW_DY, R.dimen.shadowTextY,
                                                   ATTR_SHADOW_RADIUS, R.dimen.shadowTextR,
                                                   ATTR_SHADOW_COLOR, 0x1 | COLOR,
                                                   ATTR_GRAVITY, Gravity.CENTER_VERTICAL,
                                                   ATTR_COLOR_DEFAULT, ATTR_SSH_COLOR_NORMAL | THEME,
                                                   ATTR_SIZE, R.dimen.finish,
                                                   ATTR_FONT, R.string.font_large};

    public static final int[] style_text_counters = {ATTR_SHADOW_DX, R.dimen.shadowTextX,
                                                     ATTR_SHADOW_DY, R.dimen.shadowTextY,
                                                     ATTR_SHADOW_RADIUS, R.dimen.shadowTextR,
                                                     ATTR_SHADOW_COLOR, 0x1 | COLOR,
                                                     ATTR_GRAVITY, Gravity.CENTER_VERTICAL,
                                                     ATTR_COLOR_DEFAULT, ATTR_SSH_COLOR_PANEL_COUNTERS | THEME,
                                                     ATTR_SIZE, R.dimen.panel,
                                                     ATTR_FONT, R.string.font_normal};

    public static final int[] style_panel_tile =  {ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_TILES | THEME,
                                                   ATTR_SSH_HORZ, 10,
                                                   ATTR_SSH_VERT, 4,
                                                   ATTR_CLICKABLE, 0,
                                                   ATTR_FOCUSABLE, 0,
                                                   ATTR_SSH_ALIGNED, 1,
                                                   ATTR_SSH_SCALE, TILE_SCALE_MIN,
                                                   ATTR_SSH_GRAVITY, Gravity.CENTER};

    public static final int[] style_dlg_actions = {ATTR_SSH_SHAPE, TILE_SHAPE_ROUND,
                                                   ATTR_SSH_RADII, R.string.radii_dlg};

    public static final int[] style_panel_port = {ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_PANEL_PORT | THEME};

    public static final int[] style_panel_land = {ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_PANEL_LAND | THEME};
}
