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
            <li><a href="services/WMS?SERVICE=WMS&REQUEST=GetMap&FORMAT=image/png&BBOX=-180,-90,180,90&WIDTH=600&HEIGHT=300&SRS=epsg:4326&LAYERS=ne_50m_admin_0_countries">World Map, Plate Caree</a></li>
            <li><a href="services/WMS?SERVICE=WMS&REQUEST=GetMap&FORMAT=image/png&BBOX=-180,-80,180,84&WIDTH=600&HEIGHT=350&SRS=epsg:3395&LAYERS=ne_50m_admin_0_countries">World Map, Mercator</a></li>
        </ul>
    </body>
</html>
