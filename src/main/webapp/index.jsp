<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gallows Test Links</title>
    </head>
    <body>
        <h1>Gallows Links</h1>
        <ul>
            <li><a href="services/WMS?SERVICE=WMS&REQUEST=GetCapabilities">WMS Get Capabilities</a></li>
            <li><a href="services/WMS?SERVICE=WMS&REQUEST=GetMap&FORMAT=image/png&BBOX=-180,-90,180,90&WIDTH=1000&HEIGHT=500&CRS=epsg:4326&LAYERS=ne_50m_admin_0_countries&STYLES=worldcountries&BGCOLOR=0x001166">World Map, Plate Caree</a></li>
            <li><a href="services/WMS?SERVICE=WMS&REQUEST=GetMap&FORMAT=image/png&BBOX=-20037508.3428,-15496570.7397,20037508.3428,18764656.2314&WIDTH=1000&HEIGHT=1100&CRS=epsg:3395&LAYERS=ne_50m_admin_0_countries&STYLES=worldcountries&BGCOLOR=0x001166">World Map, Mercator</a></li>
        </ul>
    </body>
</html>
