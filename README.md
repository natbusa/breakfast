breakfast
=========

How to prepare breakfast in a reactive, asynchronous way in scala

Request:

	> GET /api/v1/breakfast?eggs=2&strips=4&slices=1&juices=1&coffee=2 HTTP/1.1
	> User-Agent: curl/7.29.0
	> Host: localhost:8888
	> Accept: */*
	> Content-Type: application/json
	> 

Response:

	< HTTP/1.1 200 OK
	< Server: spray-can/1.2-RC2
	< Date: Mon, 10 Feb 2014 19:36:43 GMT
	< Content-Type: application/json; charset=UTF-8
	< Content-Length: 247
	< 
	{
	  "main": {
	    "eggs": {
	      "num": 2
	    },
	    "bacon": {
	      "strips": 4
	    }
	  },
	  "side": {
	    "toast": {
	      "slices": 1
	    }
	  },
	  "drink": {
	    "juice": {
	      "glasses": 1
	    },
	    "coffee": {
	      "mugs": 2
	    }
	  }
	}	