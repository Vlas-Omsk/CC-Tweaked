/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.shared.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

public final class ColourUtils
{
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    private static final TagKey<Item>[] DYES = new TagKey[] {
        Tags.Items.DYES_WHITE,
        Tags.Items.DYES_ORANGE,
        Tags.Items.DYES_MAGENTA,
        Tags.Items.DYES_LIGHT_BLUE,
        Tags.Items.DYES_YELLOW,
        Tags.Items.DYES_LIME,
        Tags.Items.DYES_PINK,
        Tags.Items.DYES_GRAY,
        Tags.Items.DYES_LIGHT_GRAY,
        Tags.Items.DYES_CYAN,
        Tags.Items.DYES_PURPLE,
        Tags.Items.DYES_BLUE,
        Tags.Items.DYES_BROWN,
        Tags.Items.DYES_GREEN,
        Tags.Items.DYES_RED,
        Tags.Items.DYES_BLACK,
    };

    private ColourUtils() {}

    public static DyeColor getStackColour( ItemStack stack )
    {
        if( stack.isEmpty() ) return null;

        for( int i = 0; i < DYES.length; i++ )
        {
            TagKey<Item> dye = DYES[i];
            if( stack.is( dye ) ) return DyeColor.byId( i );
        }

        return null;
    }

    public static byte[] intToBytes( int rgb )
    {
        byte b = (byte)rgb;
        byte g = (byte)(rgb >> 8);
        byte r = (byte)(rgb >> 16);

        return new byte[] { r, g, b, (byte)255 };
    }

    public static int bytesToInt( byte[] rgb )
    {
        return bytesToInt( rgb[0], rgb[1], rgb[2] );
    }

    public static int bytesToInt( byte r, byte g, byte b )
    {
        int rgb = r & 0xff;
        rgb = (rgb << 8) + (g & 0xff);
        rgb = (rgb << 8) + (b & 0xff);

        return rgb;
    }
}
