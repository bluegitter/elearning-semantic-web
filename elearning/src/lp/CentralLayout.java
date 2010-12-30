package lp;

import java.awt.LayoutManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;


public class CentralLayout implements LayoutManager
{
    int hgap, vgap;

    public CentralLayout()
    {
        this(6, 6);
    }

    public CentralLayout(int h, int v)
    {
        hgap = h;
        vgap = v;
    }

    /**
     *获得组件之间以及组件与 Container 的边之间的水平间隙
     *@return 组件之间以及组件与 Container 的边之间的水平间隙
     *@see #setHgap(int)
     */
    public int getHgap() {
        return hgap;
    }

    /**
     *设置组件之间以及组件与 Container 的边之间的水平间隙。
     *@param hgap 组件之间以及组件与 Container 的边之间的水平间隙
     *@see #getHgap()
     */
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    /**
     *获得组件之间以及组件与 Container 的边之间的水平间隙。
     *@return 组件之间以及组件与 Container 的边之间的垂直间隙
     *@see #setVgap(int)
     */
    public int getVgap() {
        return vgap;
    }

    /**
     *设置组件之间以及组件与 Container 的边之间的水平间隙。
     *@param vgap 组件之间以及组件与 Container 的边之间的水平间隙
     *@see #getVgap()
     */
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    /**实现LayoutManager类. 不在本类中使用*/
    public void addLayoutComponent(String name, Component comp) {
    }

    /**实现LayoutManager类. 不在本类中使用*/
    public void removeLayoutComponent(Component comp) {
    }

    /**
     *实现LayoutManager类, 给出指定目标容器中的 visible 组件，返回此布局的首选维数。
     *@param target 需要布置的容器
     *@return 布置指定容器的子组件的首选维数
     */
    public Dimension preferredLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim = new Dimension(0, 0);
            int nmembers = target.getComponentCount();

            for (int i = 0 ; i < nmembers ; i++) {
                Component m = target.getComponent(i);
                if (m.isVisible()) {
                    Dimension d = m.getPreferredSize();
                    dim.height = d.height;
                    dim.width = d.width;
                    break;
                }
            }
            Insets insets = target.getInsets();
            dim.width += insets.left + insets.right + hgap*2;
            dim.height += insets.top + insets.bottom + vgap*2;
            return dim;
        }
    }

    /**
     *实现LayoutManager类, 返回需要布置 visible 组件的最小维数，该组件包含在指定的目标容器中。
     *@param target 需要布置的容器
     *@return 布置指定容器的子组件的最小维数
     */
    public Dimension minimumLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim = new Dimension(0, 0);
            int nmembers = target.getComponentCount();

            for (int i = 0 ; i < nmembers ; i++) {
                Component m = target.getComponent(i);
                if (m.isVisible()) {
                    Dimension d = m.getMinimumSize();
                    dim.height = d.height;
                    dim.width  = d.width;
                    break;
                }
            }
            Insets insets = target.getInsets();
            dim.width += insets.left + insets.right + hgap*2;
            dim.height += insets.top + insets.bottom + vgap*2;
            return dim;
        }
    }

    /**
     *布置该容器。
     *<p>
     *此方法采用第一个 visible 组件的首选大小，以便满足此中央对齐方式。
     *@param target 正被布置的指定组件
     */
    public void layoutContainer(Container target) {
        synchronized (target.getTreeLock()) {
            Insets insets = target.getInsets();
            int nmembers = target.getComponentCount();
            int width  = target.getWidth() - (insets.left + insets.right + hgap*2);
            int height = target.getHeight() - (insets.top + insets.bottom + vgap*2);
            int x = insets.left + hgap, y = insets.top + vgap;

            for (int i = 0 ; i < nmembers ; i++) {
                Component m = target.getComponent(i);
                if (m.isVisible()) {
                    Dimension d = m.getPreferredSize();
                    m.setSize(d.width, d.height);

                    m.setLocation(x + (width - m.getWidth()) / 2, y + (height - m.getHeight()) / 2);
                    break;
                }
            }
        }
    }

    /**
     *返回此 <code>CentralLayout</code> 对象及其值的字符串表示形式。
     */
    public String toString() {
        String str = "";
        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + "]";
    }
}
