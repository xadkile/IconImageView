# IconImageView
#####  Apache license 2.0
IconImageView is a custom AppCompatImageView that can contains convenient methods to set color of images/icon (not the background color) to another color or a drawable at runetime or via an xml attribute.

Although this view can be used like a normal ImageView, it is best suited to displaying single-color icons with colors required to be changed quite frequently. Images used in this view should have transparent background.
# Use
```java
//call methods
setIconColor(int color)
setIconColor(Drawable drawable)
```
Or

```xml
//xml attribute
app:iconColor = "int color or drawable id"
```
