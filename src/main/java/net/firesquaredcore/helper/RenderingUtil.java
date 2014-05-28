package net.firesquaredcore.helper;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import org.lwjgl.opengl.GL11;

public class RenderingUtil
{

	public static void renderWithIcon(WavefrontObject model, IIcon icon, Tessellator tes)
	{
		for(GroupObject go : model.groupObjects)
		{
			for(Face f : go.faces) {
				Vertex n = f.faceNormal;
				tes.setNormal(n.x, n.y, n.z);
				for(int i = 0; i < f.vertices.length; i++)
				{
					Vertex v = f.vertices[i];
					TextureCoordinate t = f.textureCoordinates[i];
					tes.addVertexWithUV(v.x, v.y, v.z,
							icon.getInterpolatedU(t.u * 16),
							icon.getInterpolatedV(t.v * 16));
				}
			}
		}
	}

	public static void renderPartWithIcon(WavefrontObject model, String name, IIcon icon, Tessellator tes, int color)
	{
		for(GroupObject go : model.groupObjects)
		{
			if (go.name.equals(name))
			{
				tes.startDrawing(GL11.GL_TRIANGLES);
				if (color != -1)
					tes.setColorOpaque_I(color);
				for(Face f : go.faces)
				{
					Vertex n = f.faceNormal;
					tes.setNormal(n.x, n.y, n.z);
					for(int i = 0; i < f.vertices.length; i++)
					{
						Vertex v = f.vertices[i];
						TextureCoordinate t = f.textureCoordinates[i];
						tes.addVertexWithUV(v.x, v.y, v.z,
								icon.getInterpolatedU(t.u * icon.getIconWidth()),
								icon.getInterpolatedV(t.v * icon.getIconHeight()));
					}
				}
				tes.draw();
			}
		}
	}
}
