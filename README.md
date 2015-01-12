mysfmovies
==========
###SF Moives###
A service that shows on a map where movies have been filmed in San Francisco. The user can filter the view using autocompletion search.

![SF Movies v1.0] (http://github.com/zwlego/mysfmovies/blob/master/SF%20Moives%20v1.0.png)

It mashes up the following APIs to achieve its goal
-Google Maps
-Google GeoLocation
-DataSF

###The Technology Stack###
The following technologies are used to implement SF Movies V1.0
<ol>
<li>JavaScript | Jquery | CSS
<li>Java | Maven
<li>3rd Party APIs
</ol>


###Problems and Solutions###
This application is full functional with focus on back-end.
<ol>
<li> Source Data Redirection
<p>
Problem: The source data of SF Movies is from DataSF. Every query from a client will cause back-end server to retrieve source
data from DataSF, a third party API. This redirection can slow down the response effectively.</p>
<p>
Solution: First to retrieve the Data from DataSF and store it in the back-end Server. Now the service will use the local data  to compele a query, which will improve the response time. In addition, the service is implemented to retrieve data every desired time interval so that the data can be up to date. A little trade-off here is that the if a update has been made in DataSF, but the service has not retrieved it yet and at this time a user may get an complete result. However, the data of movies updated not often and the response time is of more importance. Another thing is the memory cost of storing source data locally, but the size of data is small. To sum up, the trade off is worthwhile.</p>
<li> Not Standard Location Address
<p>
Problem: The location information in the source data from DataSF is not standard, which will cause Google GeoLocation fail to get the coordinates of the location.</p>
<p>
Solution: If the service failed to get coordinates with the original location, the service will parse the location and try with the new location.</p>
<li> Implementation of "search in results" 
<p>
Problem: "Search in results" require service to remember the results of the current thread. </p>
<p>
Solution: Instead of storing data in the back-end, service requires the front-end to send the original results back to back-end.
</p>
</ol>

###Missing Out###
<ol>
<li>Store all the coordinates information locally 
<p>Query coordinates of all locations in the source Data once it is updated. This will make the response faster beacuse service will get the coordinates from local storage instead of redirecting to the Goecode API.</p>
<li>Combine search portals
<p> Instead of letting user input query information according to different attributes, the combined search portal lets user input all the query information into a single search portal. The back-end of the service will recognise and parse the input automatically. </p>
<li>Implement a on-map search tool
<p>
Right now the service only allows users to type in address to complete the search. By on-map search tool, user can search movies fall in desired area on map.
</p>
</ol>  

###Link###
Link to to this application: http://hazel-envoy-816.appspot.com



