/*
 * Copyright (C) 2001-2016 Food and Agriculture Organization of the
 * United Nations (FAO-UN), United Nations World Food Programme (WFP)
 * and United Nations Environment Programme (UNEP)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
 *
 * Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
 * Rome - Italy. email: geonetwork@osgeo.org
 */

(function() {

  goog.provide('gn_search_default_config');

  var module = angular.module('gn_search_default_config', []);

  module.value('gnTplResultlistLinksbtn',
      '../../catalog/views/default/directives/partials/linksbtn.html');

  module
      .run([
        'gnSearchSettings',
        'gnViewerSettings',
        'gnOwsContextService',
        'gnMap',
        'gnNcWms',
        'gnConfig',
        function(searchSettings, viewerSettings, gnOwsContextService,
                 gnMap, gnNcWms, gnConfig) {
          // Load the context defined in the configuration
          viewerSettings.defaultContext = null;
//            viewerSettings.mapConfig.viewerMap ||
//            '../../map/config-viewer.xml';

          // Keep one layer in the background
          // while the context is not yet loaded.
          viewerSettings.bgLayers = [
            gnMap.createLayerForType('osm')
          ];

          viewerSettings.servicesUrl =
            viewerSettings.mapConfig.listOfServices || {};

          // WMS settings
          // If 3D mode is activated, single tile WMS mode is
          // not supported by ol3cesium, so force tiling.
          if (gnConfig['map.is3DModeAllowed']) {
            viewerSettings.singleTileWMS = false;
            // Configure Cesium to use a proxy. This is required when
            // WMS does not have CORS headers. BTW, proxy will slow
            // down rendering.
            viewerSettings.cesiumProxy = true;
          } else {
            viewerSettings.singleTileWMS = true;
          }

          var bboxStyle = new ol.style.Style({
            stroke: new ol.style.Stroke({
              color: 'rgba(255,0,0,1)',
              width: 2
            }),
            fill: new ol.style.Fill({
              color: 'rgba(255,0,0,0.3)'
            })
          });
          searchSettings.olStyles = {
            drawBbox: bboxStyle,
            mdExtent: new ol.style.Style({
              stroke: new ol.style.Stroke({
                color: 'orange',
                width: 2
              })
            }),
            mdExtentHighlight: new ol.style.Style({
              stroke: new ol.style.Stroke({
                color: 'orange',
                width: 3
              }),
              fill: new ol.style.Fill({
                color: 'rgba(255,255,0,0.3)'
              })
            })

          };

          // Display related links in grid ?
          searchSettings.gridRelated = ['parent', 'children',
            'services', 'datasets'];

          // Object to store the current Map context
          viewerSettings.storage = 'sessionStorage';



             /*******************************************************************
             * new code
             */

//      var extent = [0.0000, 0.0000, 849024.0785, 4815054.8210];
//	    var extent = [-18133594, -11102700, 18137294, 10776300];
//	    var extent = [-4889334.802955, -4889334.802955, 4889334.802955, 4889334.802955];
//      var extent = [-180.0000, 45.0000, 180.0000, 90.0000];
      var extent = [-9014250.655095, -9014250.655095, 9014250.655095, 9014250.655095];


      var layerResolutions = [
	  136421171.96428573131561279297, 
68210585.96428573131561279297,
34105292.98928571492433547974,
17052646.49642857164144515991,
8526323.24642857164144515991,
4263161.62500000093132257462,
2131580.81178571470081806183,
1065790.40607142867520451546,
532895.20285714289639145136,
266447.60146428574807941914,
133223.80075000002398155630];

      proj4.defs(
          'EPSG:3575',
          '+proj=laea +lat_0=90 +lon_0=10 +x_0=0 +y_0=0 +ellps=WGS84 +datum=WGS84 +units=m +no_defs');

	  ol.proj.get('EPSG:3575').setExtent(extent);
      ol.proj.get('EPSG:3575').setWorldExtent([0.0000, 0.0000, 849024.0785, 4815054.8210]);
//      ol.proj.get('EPSG:3575').setWorldExtent([-9014250.655095, -9014250.655095, 9014250.655095, 9014250.655095]);
	  
	  

      proj4.defs('urn:ogc:def:crs:EPSG::3575', proj4.defs('EPSG:3575'));
      proj4.defs('http://www.opengis.net/gml/srs/epsg.xml#3575', proj4.defs('EPSG:3575'));

      var projection = ol.proj.get('EPSG:3575');
      var projectionExtent = projection.getExtent();

      var size = ol.extent.getWidth(projectionExtent) / 256;
      var resolutions = new Array(14);
      var matrixIds = new Array(14);
      for (var z = 0; z < 14; ++z) {
        // generate resolutions and matrixIds arrays for this WMTS
        resolutions[z] = size / Math.pow(2, z);
//        resolutions[z] = layerResolutions[z] * 0.00028 / projection.getMetersPerUnit();
        matrixIds[z] = z;
      }


      var tileGrid = new ol.tilegrid.WMTS({
        tileSize: 256,
        extent: projectionExtent,
        resolutions: resolutions,
        matrixIds: matrixIds
      });
//          var mapUrl = 'http://basemap.arctic-sdi.org/mapcache/wmts';
//	  var mapUrl = 'http://10.1.10.58/geonetwork/proxy?url=http%3A%2F%2Fbasemap.arctic-sdi.org%2Fmapcache%2Fwmts';
	  var mapUrl = '../../proxy?url=http%3A%2F%2Fbasemap.arctic-sdi.org%2Fmapcache%2Fwmts';
	  var source =  new ol.source.WMTS({
          url: mapUrl,
          layer: 'arctic_cascading',
		  matrixSet: '3575',
		  projection: ol.proj.get('EPSG:3575'),
          format: 'image/png',
          tileGrid: tileGrid,
          version: '1.0.0',
          style: 'default',
          crossOrigin: 'anonymous'
        });

      var wmts = new ol.layer.Tile({
        extent: extent,
		attribution: 'Arctic-SDI',
        group: 'Background layers',
        url:  mapUrl,
        source: source
      });


      /*******************************************************************
       * Define maps
       */
      var mapsConfig = {
        resolutions: resolutions,
        extent: extent,
        projection: projection,
        center: [99166,55780],
        zoom: 2
      };

      // Add backgrounds to TOC
      viewerSettings.bgLayers = [ wmts ];
      viewerSettings.servicesUrl = {};

      var viewerMap = new ol.Map({
        controls: [],
		layers: [ wmts ],
        view: new ol.View(mapsConfig)
      });

      var searchMap = new ol.Map({
        controls:[],
        layers: [ wmts ],
        view: new ol.View(angular.extend({}, mapsConfig))
      });
             /*******************************************************************
             * end of new code
             */



          /*******************************************************************
             * Define maps
             */
//          var mapsConfig = {
//            center: [280274.03240585705, 6053178.654789996],
//            zoom: 2
//            //maxResolution: 9783.93962050256
//          };

//          var viewerMap = new ol.Map({
//            controls: [],
//            view: new ol.View(mapsConfig)
//          });

//          var searchMap = new ol.Map({
//            controls:[],
//            layers: [new ol.layer.Tile({
//              source: new ol.source.OSM()
//            })],
//            view: new ol.View(angular.extend({}, mapsConfig))
//          });


          /** Facets configuration */
          searchSettings.facetsSummaryType = 'details';

          /*
             * Hits per page combo values configuration. The first one is the
             * default.
             */
          searchSettings.hitsperpageValues = [20, 50, 100];

          /* Pagination configuration */
          searchSettings.paginationInfo = {
            hitsPerPage: searchSettings.hitsperpageValues[0]
          };

          /*
             * Sort by combo values configuration. The first one is the default.
             */
          searchSettings.sortbyValues = [{
            sortBy: 'relevance',
            sortOrder: ''
          }, {
            sortBy: 'changeDate',
            sortOrder: ''
          }, {
            sortBy: 'title',
            sortOrder: 'reverse'
          }, {
            sortBy: 'rating',
            sortOrder: ''
          }, {
            sortBy: 'popularity',
            sortOrder: ''
          }, {
            sortBy: 'denominatorDesc',
            sortOrder: ''
          }, {
            sortBy: 'denominatorAsc',
            sortOrder: 'reverse'
          }];

          /* Default search by option */
          searchSettings.sortbyDefault = searchSettings.sortbyValues[0];

          /* Custom templates for search result views */
          searchSettings.resultViewTpls = [{
                  tplUrl: '../../catalog/components/search/resultsview/' +
                  'partials/viewtemplates/grid.html',
                  tooltip: 'Grid',
                  icon: 'fa-th'
                }];

          // For the time being metadata rendering is done
          // using Angular template. Formatter could be used
          // to render other layout

          // TODO: formatter should be defined per schema
          // schema: {
          // iso19139: 'md.format.xml?xsl=full_view&&id='
          // }
          searchSettings.formatter = {
            // defaultUrl: 'md.format.xml?xsl=full_view&id='
            // defaultUrl: 'md.format.xml?xsl=xsl-view&uuid=',
            // defaultPdfUrl: 'md.format.pdf?xsl=full_view&uuid=',
            list: [{
              label: 'full',
              url : function(md) {
                return '../api/records/' + md.getUuid() + '/formatters/xsl-view?root=div&view=advanced';
              }
            }]
          };

          // Mapping for md links in search result list.
          searchSettings.linkTypes = {
            links: ['LINK', 'kml'],
            downloads: ['DOWNLOAD'],
            //layers:['OGC', 'kml'],
            layers:['OGC'],
            maps: ['ows']
          };

          // Map protocols used to load layers/services in the map viewer
          searchSettings.mapProtocols = {
            layers: [
              'OGC:WMS',
              'OGC:WMS-1.1.1-http-get-map',
              'OGC:WMS-1.3.0-http-get-map',
              'OGC:WFS'
              ],
            services: [
              'OGC:WMS-1.3.0-http-get-capabilities',
              'OGC:WMS-1.1.1-http-get-capabilities',
              'OGC:WFS-1.0.0-http-get-capabilities'
              ]
          };

          // Set the default template to use
          searchSettings.resultTemplate =
              searchSettings.resultViewTpls[0].tplUrl;

          // Set custom config in gnSearchSettings
          angular.extend(searchSettings, {
            viewerMap: viewerMap,
            searchMap: searchMap
          });

          viewerMap.getLayers().on('add', function(e) {
            var layer = e.element;
            if (layer.get('advanced')) {
              gnNcWms.feedOlLayer(layer);
            }
          });

        }]);
})();
