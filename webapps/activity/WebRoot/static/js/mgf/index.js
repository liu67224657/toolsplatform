(function (lib, img, cjs, ss) {

	var p; // shortcut to reference prototypes
	lib.webFontTxtFilters = {};

// library properties:
	lib.properties = {
		width: 750,
		height: 1205,
		fps: 60,
		color: "#FFFFFF",
		webfonts: {},
		manifest: [
			{src:"/static/js/mgf/images/BANDAINAMCOGAMES.png", id:"BANDAINAMCOGAMES"},
			{src:"/static/js/mgf/images/bgtest.jpg", id:"bgtest"},
			{src:"/static/js/mgf/images/Blizzard.png", id:"Blizzard"},
			{src:"/static/js/mgf/images/Colopl.png", id:"Colopl"},
			{src:"/static/js/mgf/images/dc.jpg", id:"dc"},
			{src:"/static/js/mgf/images/de.png", id:"de"},
			{src:"/static/js/mgf/images/EA.png", id:"EA"},
			{src:"/static/js/mgf/images/Glu.png", id:"Glu"},
			{src:"/static/js/mgf/images/NCsoft.png", id:"NCsoft"},
			{src:"/static/js/mgf/images/p1.png", id:"p1"},
			{src:"/static/js/mgf/images/p1_1.png", id:"p1_1"},
			{src:"/static/js/mgf/images/p1_2.png", id:"p1_2"},
			{src:"/static/js/mgf/images/p1_3.png", id:"p1_3"},
			{src:"/static/js/mgf/images/p2.png", id:"p2"},
			{src:"/static/js/mgf/images/p3_1.png", id:"p3_1"},
			{src:"/static/js/mgf/images/p3_2.png", id:"p3_2"},
			{src:"/static/js/mgf/images/p3_3.png", id:"p3_3"},
			{src:"/static/js/mgf/images/p3_5.png", id:"p3_5"},
			{src:"/static/js/mgf/images/p3_6.png", id:"p3_6"},
			{src:"/static/js/mgf/images/p3_7.png", id:"p3_7"},
			{src:"/static/js/mgf/images/p3_8.png", id:"p3_8"},
			{src:"/static/js/mgf/images/p3_9.png", id:"p3_9"},
			{src:"/static/js/mgf/images/p4_1.png", id:"p4_1"},
			{src:"/static/js/mgf/images/p4_2.png", id:"p4_2"},
			{src:"/static/js/mgf/images/p4_3.png", id:"p4_3"},
			{src:"/static/js/mgf/images/Rovio.png", id:"Rovio"},
			{src:"/static/js/mgf/images/u1.png", id:"u1"},
			{src:"/static/js/mgf/images/u2.png", id:"u2"},
			{src:"/static/js/mgf/images/u3.png", id:"u3"},
			{src:"/static/js/mgf/images/u4.png", id:"u4"},
			{src:"/static/js/mgf/images/u5.png", id:"u5"},
			{src:"/static/js/mgf/images/u6.png", id:"u6"},
			{src:"/static/js/mgf/images/u7.png", id:"u7"},
			{src:"/static/js/mgf/images/u8.png", id:"u8"},
			{src:"/static/js/mgf/images/u9.png", id:"u9"},
			{src:"/static/js/mgf/img/r1.jpg", id:""},
			{src:"/static/js/mgf/img/r2.jpg", id:""},
			{src:"/static/js/mgf/img/r3.jpg", id:""},
			{src:"/static/js/mgf/img/r4.jpg", id:""},
			{src:"/static/js/mgf/img/r5.jpg", id:""},
			{src:"/static/js/mgf/img/r6.jpg", id:""},
			{src:"/static/js/mgf/img/r7.jpg", id:""},
			{src:"/static/js/mgf/img/r8.jpg", id:""},
			{src:"/static/js/mgf/img/v1.jpg", id:""},
			{src:"/static/js/mgf/img/v2.jpg", id:""},
			{src:"/static/js/mgf/img/v3.jpg", id:""},
			{src:"/static/js/mgf/img/v4.jpg", id:""},
			{src:"/static/js/mgf/img/zmwl.png", id:"zmwl"}
		]
	};



	lib.webfontAvailable = function(family) {
		lib.properties.webfonts[family] = true;
		var txtFilters = lib.webFontTxtFilters && lib.webFontTxtFilters[family] || [];
		for(var f = 0; f < txtFilters.length; ++f) {
			txtFilters[f].updateCache();
		}
	};
// symbols:



	(lib.BANDAINAMCOGAMES = function() {
		this.initialize(img.BANDAINAMCOGAMES);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,350,139);


	(lib.bgtest = function() {
		this.initialize(img.bgtest);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,750,1205);


	(lib.Blizzard = function() {
		this.initialize(img.Blizzard);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,350,139);


	(lib.Colopl = function() {
		this.initialize(img.Colopl);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,350,139);


	(lib.dc = function() {
		this.initialize(img.dc);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,56,46);


	(lib.de = function() {
		this.initialize(img.de);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,113,113);


	(lib.EA = function() {
		this.initialize(img.EA);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,350,139);


	(lib.Glu = function() {
		this.initialize(img.Glu);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,350,139);


	(lib.NCsoft = function() {
		this.initialize(img.NCsoft);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,350,139);


	(lib.p1 = function() {
		this.initialize(img.p1);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,610,621);


	(lib.p1_1 = function() {
		this.initialize(img.p1_1);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,432,265);


	(lib.p1_2 = function() {
		this.initialize(img.p1_2);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,543,210);


	(lib.p1_3 = function() {
		this.initialize(img.p1_3);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,630,201);


	(lib.p2 = function() {
		this.initialize(img.p2);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,447,456);


	(lib.p3_1 = function() {
		this.initialize(img.p3_1);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,339,116);


	(lib.p3_2 = function() {
		this.initialize(img.p3_2);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,750,368);


	(lib.p3_3 = function() {
		this.initialize(img.p3_3);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,750,524);


	(lib.p3_5 = function() {
		this.initialize(img.p3_5);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,74,108);


	(lib.p3_6 = function() {
		this.initialize(img.p3_6);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,218,48);


	(lib.p3_7 = function() {
		this.initialize(img.p3_7);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,205,48);


	(lib.p3_8 = function() {
		this.initialize(img.p3_8);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,213,48);


	(lib.p3_9 = function() {
		this.initialize(img.p3_9);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,271,48);


	(lib.p4_1 = function() {
		this.initialize(img.p4_1);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,688,519);


	(lib.p4_2 = function() {
		this.initialize(img.p4_2);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,485,72);


	(lib.p4_3 = function() {
		this.initialize(img.p4_3);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,485,179);


	(lib.Rovio = function() {
		this.initialize(img.Rovio);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,350,139);


	(lib.u1 = function() {
		this.initialize(img.u1);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,98,98);


	(lib.u2 = function() {
		this.initialize(img.u2);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,98,98);


	(lib.u3 = function() {
		this.initialize(img.u3);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,98,98);


	(lib.u4 = function() {
		this.initialize(img.u4);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,98,98);


	(lib.u5 = function() {
		this.initialize(img.u5);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,98,98);


	(lib.u6 = function() {
		this.initialize(img.u6);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,98,98);


	(lib.u7 = function() {
		this.initialize(img.u7);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,98,98);


	(lib.u8 = function() {
		this.initialize(img.u8);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,98,98);


	(lib.u9 = function() {
		this.initialize(img.u9);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,307,366);


	(lib.zmwl = function() {
		this.initialize(img.zmwl);
	}).prototype = p = new cjs.Bitmap();
	p.nominalBounds = new cjs.Rectangle(0,0,350,139);


	(lib.t8 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("#FFFFFF").s().p("AgRAnQgHgCgDgFQgDgFgBgFIgBgOIAAgxIAOAAIAAAsIABAPQABAFAFADQAEAEAGAAQAEAAAGgEQAGgCACgHQACgFAAgLIAAgqIAOAAIAABRIgMAAIAAgMQgKAOgPAAQgHAAgGgDg");
		this.shape.setTransform(-37.6,15.1);

		this.shape_1 = new cjs.Shape();
		this.shape_1.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape_1.setTransform(-43.8,13.4);

		this.shape_2 = new cjs.Shape();
		this.shape_2.graphics.f("#FFFFFF").s().p("AgYA0QgOgIgGgNQgIgOAAgRQAAgPAIgOQAGgPANgHQAOgHAPAAQAMAAALAEQAKAEAFAIQAGAHADALIgOAEQgDgJgEgFQgDgFgIgDQgGgDgJAAQgJAAgHADQgIAEgEAFQgFAFgDAGQgEALAAALQAAAOAFALQAGAKAKAFQAKAFAJAAQAKAAAKgEQAJgEAEgEIAAgVIghAAIAAgMIAxAAIAAApQgMAJgMAEQgMAFgMAAQgPAAgOgHg");
		this.shape_2.setTransform(-51.8,13.4);

		this.shape_3 = new cjs.Shape();
		this.shape_3.graphics.f("#FFFFFF").s().p("AgoAYIgBg1IAKAGIA3AAIAGgHIAJAJIgFAEIABAZIgKAEIAAgHIg4AAIAAAVIA8AAIAGgGIAJAIIgFAEIABAiIgKAEIAAgIIg9AAIAAAHIgKAEIABgxgAgfA5IA9AAIAAgcIg9AAgAgfACIA4AAIAAgWIg4AAgAhCgXQgEgDAGgGQAGgEAEgPIADAAIAAAGIBlAAIAHgGIALAMQgKAAgMAQIgDgCIAIgQIhmAAQAAANgFAEQgCACgDAAIgFgBgAgDgzQgDgKgJgJIACgBQAMAFADAFQAGAEgFAIQgCADgBAAQgBAAgCgFg");
		this.shape_3.setTransform(14.2,-6.6);

		this.shape_4 = new cjs.Shape();
		this.shape_4.graphics.f("#FFFFFF").s().p("AglARQgUALgGAJIgHgKQADgDAAgGIAAgkIgBgaIAOAHIgFAEIAAA3IAWgIIgBhWIAOAHIgFAFIAACBIgJAFIABg5gAgZBGQAYgMASgXQgRgegBgiQgIAUgMAOIgCgBQASggAGgtIAQAIIgGADIgLAcIAwAAIAIgJIAMAMIgVAAQgGAmgOAcQAWAXAWABIAAADQgLAAgEAIQgVgLgNgSQgVAVgZAKgAAWAbQAJgSAHgoIgmAAQAGAkAQAWg");
		this.shape_4.setTransform(-1.7,-6.6);

		this.shape_5 = new cjs.Shape();
		this.shape_5.graphics.f("#FFFFFF").s().p("AgvAyIAAgYIAJAFIBHAAIAFgHIAKAIIgFAEIABAfIgKAEIAAgJIhIAAIAAAHIgJADIAAgWgAgmA7IBIAAIAAgYIhIAAgAAZARIg2AAIAAACIgJAEIABgRIgBgTIAJAFIA1AAIAFgGIAKAHIgGAEIABAWIgJAEgAgdAOIA2AAIAAgSIg2AAgAg/gFQgDgBAFgGQAFgHACgNIADAAIAAAHIBmAAIAGgHIALALQgKABgMAQIgCgCIAIgPIhnAAQABAJgBADQgBADgEABIgDABIgEgBgAgYgwIgXAAQgIAAgHACIgFgGIArAAIAAgTIAOAGIgFAEIAAAJIAcAAIAAgTIAOAGIgFAEIAAAJIAYAAIAKgJIALANIgtAAIABANIgKAEIAAgRIgcAAIAAAOIgJAEIAAgSg");
		this.shape_5.setTransform(-17.9,-6.5);

		this.shape_6 = new cjs.Shape();
		this.shape_6.graphics.f("#FFFFFF").s().p("AAGATIgbAAIAAAnIgJAEIABgbIgBgXIAKAEIAaAAIAAgQIgKAAIAAAEIgJACIABgbIgSAAIgGABIgEgGIAcAAIgBgUIANAHIgEADIAAAKIAdAAIgBgUIAOAHIgFADIAAAKIAPAAIAIgIIAMANIgjAAIAAAYIgIACIAAgFIgLAAIAAAQIAhAAIAEgGIAJAHIgEAFIAAAXQABAMgOAEQAAgHgPgEIAAgCIAOABQAGAAAAgIIAAgWIgiAAIABAyIgKADIABg1gAgEgDIAdAAIAAgSIgdAAgAhGBFQAPgfAAgmIAAg5IAKAFIAyAAQgCgKgGgJIABgBQAMAFACAFQADAEgFAGIApAAIAJgJIALANIh0AAQAAAogDAYQgCAcgSAag");
		this.shape_6.setTransform(-34.1,-6.5);

		this.shape_7 = new cjs.Shape();
		this.shape_7.graphics.f("#FFFFFF").s().p("AgoAbIgBgwIAJAFIASAAQAEgKABgLIgsAAQgEAAgHACIgGgFIAzAAIgEgNQgCgGgIgIIABgCQAOAHAGAGQAGAHgIAJIAXAAQAJgQAEgQIAOAJIgHACIgPAVIAiAAIAKgJIANAMIhGAAQgEAIgGANIAnAAIAFgHIAKAHIgEAFIAAA8IAAASIgJAFIAAgNIhAAAIAAAKIgJADQABgHAAgmgAggA2IBAAAIAAgVIhAAAgAggAeIBAAAIAAgVIhAAAgAggAGIBAAAIAAgSIhAAAg");
		this.shape_7.setTransform(-49.9,-6.5);

		this.shape_8 = new cjs.Shape();
		this.shape_8.graphics.f("#FFFFFF").s().p("AATAqIAAgwQAAgJgBgFQgCgEgEgCQgEgDgGAAQgHAAgHAGQgGAGAAAPIAAAsIgOAAIAAhRIAMAAIAAAMQAKgOAPAAQAHAAAHADQAGACADAFQADAFABAFIABAOIAAAxg");
		this.shape_8.setTransform(41.6,-24.9);

		this.shape_9 = new cjs.Shape();
		this.shape_9.graphics.f("#FFFFFF").s().p("AgeAkQgGgHAAgKQAAgGACgFQADgFAFgDQAEgBAGgCIAMgCQAPgCAIgCIAAgFQAAgIgEgDQgFgFgKAAQgJAAgFAEQgFADgCAIIgNgBQACgJAEgGQAEgFAJgDQAHgDAJAAQALAAAHACQAGADADAEQAEAEAAAFIABAOIAAAQQAAAUABAFIAEAKIgPAAIgDgKQgIAGgHADQgFADgIAAQgOAAgIgHgAgCAEIgMAEQgEABgCADQgBADgBADQAAAGAFAEQAEAEAIAAQAGAAAHgEQAGgDADgHQACgEAAgKIAAgEQgHACgOACg");
		this.shape_9.setTransform(32.7,-24.8);

		this.shape_10 = new cjs.Shape();
		this.shape_10.graphics.f("#FFFFFF").s().p("AgFApIgghRIAQAAIARAwIAEARIAFgQIASgxIAPAAIggBRg");
		this.shape_10.setTransform(24.3,-24.8);

		this.shape_11 = new cjs.Shape();
		this.shape_11.graphics.f("#FFFFFF").s().p("AgdAkQgIgHABgKQgBgGADgFQADgFAFgDQAEgBAFgCIANgCQAPgCAIgCIAAgFQAAgIgEgDQgFgFgKAAQgJAAgFAEQgEADgCAIIgPgBQADgJAEgGQAEgFAIgDQAJgDAIAAQALAAAGACQAHADADAEQAEAEABAFIAAAOIAAAQQAAAUABAFIADAKIgOAAIgDgKQgHAGgIADQgFADgIAAQgOAAgHgHgAgCAEIgMAEQgEABgCADQgCADAAADQABAGAEAEQAEAEAIAAQAGAAAHgEQAGgDADgHQACgEAAgKIAAgEQgIACgNACg");
		this.shape_11.setTransform(15.8,-24.8);

		this.shape_12 = new cjs.Shape();
		this.shape_12.graphics.f("#FFFFFF").s().p("AATA5IAAg1QAAgIgEgFQgFgFgIAAQgEAAgGADQgFAEgDAFQgCAEAAAJIAAAuIgOAAIAAhxIAOAAIAAApQAKgLANAAQAJAAAHAEQAHADACAHQADAGAAAKIAAA1g");
		this.shape_12.setTransform(6.9,-26.4);

		this.shape_13 = new cjs.Shape();
		this.shape_13.graphics.f("#FFFFFF").s().p("AAQA5IgZgrIgKAKIAAAhIgOAAIAAhxIAOAAIAABAIAgggIASAAIgfAdIAiA0g");
		this.shape_13.setTransform(-1,-26.4);

		this.shape_14 = new cjs.Shape();
		this.shape_14.graphics.f("#FFFFFF").s().p("AAjA5IgNgjIgtAAIgNAjIgQAAIAshxIAOAAIAvBxgAgGgWIgNAgIAkAAIgMgeIgGgYQgCALgDALg");
		this.shape_14.setTransform(-10.8,-26.4);

		this.shape_15 = new cjs.Shape();
		this.shape_15.graphics.f("#FFFFFF").s().p("AgWAkQgJgGgCgOIAOgCQABAJAFAEQAFAFAJgBQAJAAAEgEQAGgDAAgGQgBgFgEgDQgCgCgMgCQgNgEgHgCQgFgCgEgFQgDgFAAgGQABgGACgEQADgFADgDQAEgCAFgCQAGgCAHAAQAIAAAHADQAHADAEAEQADAGABAIIgNACQgBgHgEgDQgFgFgHAAQgJAAgEAEQgEADAAAFIACAEIAFAEIALAEQAPADAFAEQAGAAADAEQAEAFAAAIQAAAGgEAHQgEAGgIADQgIAEgJAAQgPAAgIgHg");
		this.shape_15.setTransform(-24.7,-24.8);

		this.shape_16 = new cjs.Shape();
		this.shape_16.graphics.f("#FFFFFF").s().p("AgFA5IAAhRIALAAIAABRgAgFgoIAAgQIALAAIAAAQg");
		this.shape_16.setTransform(-30.4,-26.4);

		this.shape_17 = new cjs.Shape();
		this.shape_17.graphics.f("#FFFFFF").s().p("AgVAqIAAhRIAMAAIAAANQAGgJADgDQADgDAEAAQAIAAAHAFIgFAMQgFgCgFAAQgFAAgCACQgDADgBAFQgDAIAAAIIAAAqg");
		this.shape_17.setTransform(-34.2,-24.9);

		this.shape_18 = new cjs.Shape();
		this.shape_18.graphics.f("#FFFFFF").s().p("AATA5IAAg1QAAgIgEgFQgFgFgIAAQgEAAgGADQgFAEgDAFQgCAEAAAJIAAAuIgOAAIAAhxIAOAAIAAApQAKgLANAAQAJAAAHAEQAHADACAHQADAGAAAKIAAA1g");
		this.shape_18.setTransform(-42,-26.4);

		this.shape_19 = new cjs.Shape();
		this.shape_19.graphics.f("#FFFFFF").s().p("AgZAzQgMgHgGgPQgGgOAAgPQAAgRAHgOQAHgNANgHQAMgHANAAQARAAAMAJQAMAJAEAQIgPADQgEgMgHgGQgIgGgLAAQgLAAgKAHQgIAGgEALQgEAKABALQgBANAFALQAEALAJAFQAJAGAJAAQANAAAIgIQAJgHADgOIAPADQgFATgMAKQgMAKgSAAQgRAAgLgIg");
		this.shape_19.setTransform(-52.1,-26.4);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_19},{t:this.shape_18},{t:this.shape_17},{t:this.shape_16},{t:this.shape_15},{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-60,-37.2,142,61.7);


	(lib.t7 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("#FFFFFF").s().p("AgVA2QgLgEgGgJQgGgKAAgLIAOgBQABAIAEAGQAEAFAHAEQAIADAIAAQAJAAAGgCQAHgDADgEQAEgFAAgFQAAgGgEgEQgDgEgHgDIgSgFQgQgEgGgBQgJgFgEgGQgEgGAAgIQAAgJAFgHQAFgIAKgEQAJgEALAAQALAAAKAEQAKAEAFAIQAFAIABAKIgPACQgBgLgHgGQgHgFgMAAQgMAAgHAFQgGAFAAAHQAAAGAEAEQAFAEAQAEQATAEAHADQAKADAEAHQAFAHAAAJQAAAKgFAIQgFAIgKAEQgKAFgMAAQgOAAgKgFg");
		this.shape.setTransform(-5.6,33.3);

		this.shape_1 = new cjs.Shape();
		this.shape_1.graphics.f("#FFFFFF").s().p("AgpA5IAAhxIBRAAIAAAOIhCAAIAAAjIA9AAIAAALIg9AAIAAAnIBEAAIAAAOg");
		this.shape_1.setTransform(-16,33.3);

		this.shape_2 = new cjs.Shape();
		this.shape_2.graphics.f("#FFFFFF").s().p("AAoA5IAAheIghBeIgNAAIgghgIAABgIgPAAIAAhxIAXAAIAbBPIADARIAHgSIAbhOIAUAAIAABxg");
		this.shape_2.setTransform(-28.2,33.3);

		this.shape_3 = new cjs.Shape();
		this.shape_3.graphics.f("#FFFFFF").s().p("AAjA5IgMgjIguAAIgNAjIgQAAIAshxIAOAAIAvBxgAgHgWIgMAgIAkAAIgMgeIgGgYQgBALgFALg");
		this.shape_3.setTransform(-40.2,33.3);

		this.shape_4 = new cjs.Shape();
		this.shape_4.graphics.f("#FFFFFF").s().p("AgYA0QgOgIgGgNQgIgOAAgRQAAgPAIgOQAGgPANgHQAOgHAPAAQAMAAALAEQAKAEAFAIQAGAHADALIgOAEQgDgJgEgFQgDgFgIgDQgGgDgJAAQgJAAgHADQgIAEgEAFQgFAFgDAGQgEALAAALQAAAOAFALQAGAKAKAFQAKAFAJAAQAKAAAKgEQAJgEAEgEIAAgVIghAAIAAgMIAxAAIAAApQgMAJgMAEQgMAFgMAAQgPAAgOgHg");
		this.shape_4.setTransform(-51.8,33.3);

		this.shape_5 = new cjs.Shape();
		this.shape_5.graphics.f("#FFFFFF").s().p("AgbAzQgNgIgGgNQgIgOABgPQAAgbAPgQQAPgQAXAAQAQAAAMAIQANAHAGAOQAIANgBAQQAAARgHAOQgHAOgNAHQgNAHgOAAQgOAAgNgIgAgagiQgMALABAYQAAAVALAMQALAMAPAAQAQAAALgMQAMgMgBgWQABgMgFgLQgFgKgJgGQgJgGgLAAQgOAAgMALg");
		this.shape_5.setTransform(59.3,13.4);

		this.shape_6 = new cjs.Shape();
		this.shape_6.graphics.f("#FFFFFF").s().p("AgaAzQgLgHgGgPQgGgOAAgPQAAgRAHgOQAGgNANgHQANgHANAAQARAAAMAJQALAJAGAQIgPADQgEgMgIgGQgIgGgMAAQgLAAgIAHQgJAGgEALQgDAKgBALQABANAEALQAEALAJAFQAJAGAIAAQAOAAAIgIQAJgHADgOIAPADQgEATgNAKQgNAKgRAAQgQAAgNgIg");
		this.shape_6.setTransform(47.4,13.4);

		this.shape_7 = new cjs.Shape();
		this.shape_7.graphics.f("#FFFFFF").s().p("AAnA5IAAheIghBeIgLAAIgihgIAABgIgOAAIAAhxIAWAAIAcBPIAEARIAFgSIAchOIAUAAIAABxg");
		this.shape_7.setTransform(34.8,13.4);

		this.shape_8 = new cjs.Shape();
		this.shape_8.graphics.f("#FFFFFF").s().p("AAkA5IgOgjIguAAIgMAjIgQAAIAshxIAOAAIAvBxgAgHgWIgMAgIAlAAIgMgeIgGgYQgCALgFALg");
		this.shape_8.setTransform(22.9,13.4);

		this.shape_9 = new cjs.Shape();
		this.shape_9.graphics.f("#FFFFFF").s().p("AAdA5Ig6hYIAABYIgPAAIAAhxIAQAAIA6BYIAAhYIAPAAIAABxg");
		this.shape_9.setTransform(11.7,13.4);

		this.shape_10 = new cjs.Shape();
		this.shape_10.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape_10.setTransform(-0.7,13.4);

		this.shape_11 = new cjs.Shape();
		this.shape_11.graphics.f("#FFFFFF").s().p("AAjA5IgMgjIguAAIgNAjIgQAAIAshxIAOAAIAvBxgAgHgWIgMAgIAkAAIgMgeIgGgYQgBALgFALg");
		this.shape_11.setTransform(-8.2,13.4);

		this.shape_12 = new cjs.Shape();
		this.shape_12.graphics.f("#FFFFFF").s().p("AguA5IAAhxIAoAAQALAAAHACQAKACAHAGQAJAIAEAMQAFAMAAAOQAAAMgDAKQgDAKgFAHQgFAGgFAEQgGAEgIACQgIACgJAAgAgfArIAZAAQAJAAAHgCQAGgCAEgEQAFgFADgJQADgJAAgMQAAgRgGgKQgGgKgIgDQgGgCgMAAIgYAAg");
		this.shape_12.setTransform(-19.2,13.4);

		this.shape_13 = new cjs.Shape();
		this.shape_13.graphics.f("#FFFFFF").s().p("AAdA5Ig6hYIAABYIgPAAIAAhxIAQAAIA6BYIAAhYIAPAAIAABxg");
		this.shape_13.setTransform(-31,13.4);

		this.shape_14 = new cjs.Shape();
		this.shape_14.graphics.f("#FFFFFF").s().p("AAjA5IgMgjIguAAIgNAjIgQAAIAshxIAOAAIAvBxgAgHgWIgMAgIAkAAIgMgeIgGgYQgBALgFALg");
		this.shape_14.setTransform(-42,13.4);

		this.shape_15 = new cjs.Shape();
		this.shape_15.graphics.f("#FFFFFF").s().p("AgqA5IAAhxIAqAAQANAAAIAEQAHADAFAHQAEAIAAAIQAAAHgEAGQgEAHgIAEQALACAFAGQAGAIAAAKQAAAIgDAHQgEAGgFAEQgFAEgHACQgIACgLAAgAgaArIAaAAIAKAAQAGgBADgCQAEgDACgEQACgEAAgFQAAgHgDgFQgDgEgGgCQgGgCgJAAIgaAAgAgagIIAYAAIANgBQAGgCADgEQACgEAAgGQAAgFgCgFQgDgEgFgCQgFgBgKAAIgXAAg");
		this.shape_15.setTransform(-52.5,13.4);

		this.shape_16 = new cjs.Shape();
		this.shape_16.graphics.f("#FFFFFF").s().p("AgOBFQAYgMARgTQgHgNgCgPIgDgbIhCAAQgIAAgHACIgFgGIApAAIAAgUIgMAAQgIAAgHACIgGgFIAhAAIgBgbIAPAIIgGAEIAAAPIANAAIAHgHIAJAKIgdAAIAAAUIAlAAIgBgyIAPAHIgFAEIAAAnIAcAAIAIgJIAMANIgwAAQABAfAHAQQANgTAHgQIAMAHIgGACQgMAVgKANQALANANAHIAIgcIACABQgCAQAAAJQABAJABAEQACAEgGgBQgGgCgKgHQgKgHgJgMQgTATgZAJgAgtA6QACgEAAgDIAAgWQgKAMgQAJIgCgCQAZgTALgYIgNAAQgIAAgHACIgGgFIBBAAIAGgGIAKAJIgmAAIgIAPIAAAiIAXgKIABACQgXAQgFAFgAgFAtQgIgLgRgNIABgBIAPAIQAKgKAEgJIAIAJIgIADIgMAIQAKAGADAEQAFAFgDAGQAAABAAAAQgBABAAAAQAAAAgBAAQAAABAAAAQgBAAgFgIgAggAAQgCgFgIgKIACgBIANAIQAEADgDAFQgBAAAAAAQgBAAAAAAQgBABAAAAQAAAAgBAAQAAAAAAAAQgBAAAAgBQAAAAAAAAQgBAAAAAAgAAtgnQgDgJgIgKIABgBQAOAJAEAEQAEAFgEAGQgBAAAAAAQAAABgBAAQAAAAAAAAQgBABAAAAQgDAAgCgGg");
		this.shape_16.setTransform(46,-6.6);

		this.shape_17 = new cjs.Shape();
		this.shape_17.graphics.f("#FFFFFF").s().p("AgQBFQgOAAABgNIAAghIAOAIIgFAEIAAATQAAAHAGAAIAlAAQAGAAABgGQACgHAAgNIADAAIABASQABAFAGACQgEAJgKAAgAhBA6QgDgCAIgHQAIgGAFgPIACAAQgBAUgDAFQgDAGgFABIgCAAQgDAAgDgCgAA5AzQgFgKgOgNIABgCQAMAFAHAFQAIAEABAFQABAFgDAFQgBABAAAAQAAABgBAAQAAAAAAAAQgBAAAAAAQgDAAgCgGgAADAmQgDgJgLgNIACgBQANAHAFAFQAGAFgEAHQgBABAAABQgBAAAAABQgBAAAAAAQgBAAAAAAQgCAAgCgEgAgngHIAAgcIAJAFIAlAAQALgUAFgSIANAKIgGABIgTAbIAZAAIAEgGIAKAIIgFAEQAAAdABAKIgKAEIAAgHIhCAAIAAAFIgJADIAAgbgAgeAIIBCAAIAAgiIhCAAgAgSgpQgBgJgJgNIACgCQAKAHAFAFQAFAFgBAFQgCAFgDADIgCABQgDAAgBgHg");
		this.shape_17.setTransform(29.8,-6.9);

		this.shape_18 = new cjs.Shape();
		this.shape_18.graphics.f("#FFFFFF").s().p("AAgA6IAAgCIAUABQABAAAAAAQABgBAAAAQAAgBAAAAQAAgBAAgBIAAhjIAAgZIAOAHIgFAEIAABzQAAAHgDADQgCAEgHADQgDgJgQgFgAg+AiIAAgiIAJACIA2AAIAFgDIAJAGIgEADIAAApIAAANIgJAFIAAgKIg4AAIAAAKIgIADIAAgkgAgVA1IAXAAIAAgXIgXAAgAg2A1IAaAAIAAgXIgaAAgAgVAaIAXAAIAAgUIgXAAgAg2AaIAaAAIAAgUIgaAAgAAYgJIAAgoIAPAIIgGADIAAAwIAAASIgJAFIAAgqgAgzgaIgBgVIAJAFIAlAAIAGgGIAGAIIgFAEIAAALIABAOIgGADIAAgHIgnAAIAAAHIgJADIABgVgAgrgSIAnAAIAAgUIgnAAgAhDg7IBDAAIAFgHIALALIg+AAQgLAAgFACg");
		this.shape_18.setTransform(13.8,-6.4);

		this.shape_19 = new cjs.Shape();
		this.shape_19.graphics.f("#FFFFFF").s().p("AgwBHQAngNARgUQgMgSgJgUIgFAAQgHATgMASQgNASgUAOIAAgCQAKgJAJgNQAJgNAIgRQAHgPAFgbIgcAAIgFAGIgIgJIAFgDQAIgUACgLIANAHIgFAEIgKAWIAdAAIAEgVIACgUIANAGIgFAEIgFAfIA8AAIAJgIIAMAMIhSAAIgEAXIAzAAIAEgHIALAKIgFACQgLAVgPAQQAUARAiAGIAAACQgKACgCAHQgegLgRgRQgWAVgmAIgAAMAgQAMgNAKgTIgrAAQAJASAMAOgAAjgsQgGgJgQgOIACgCQASAIAGAFQAHAFgDAIQgBAEgCAAQgDAAgCgFg");
		this.shape_19.setTransform(-1.9,-6.6);

		this.shape_20 = new cjs.Shape();
		this.shape_20.graphics.f("#FFFFFF").s().p("AhFBEQAUgNALgRQALgRABgaIgZAAQgIAAgHACIgFgGIAtAAIAAgvIgQAAQgJAAgGACIgGgFIBpAAIAKgKIAMANIgjAAIAAAvIAUAAIAKgKIANAOIgrAAIAAAvIAAAXIgKAEIAAggIAAgqIgkAAQgBAZgLASQgMARgaAPgAgRgJIAkAAIAAgvIgkAAg");
		this.shape_20.setTransform(-18,-6.3);

		this.shape_21 = new cjs.Shape();
		this.shape_21.graphics.f("#FFFFFF").s().p("Ag8AhIgBghIAKAFIAdAAIAFgFIAKAHIgFAFIABAxIgKAEIAAgNIgeAAIAAAOIgKADIABgkgAgzAxIAeAAIAAgoIgeAAgAAHAhIgBghIAJAFIAgAAIAFgFIAKAHIgFAEIAAAzIgJAEIAAgOIghAAIAAAMIgJAEIABgjgAAPAxIAhAAIAAgoIghAAgAgjgnIgBgdIAKAGIAwAAIAFgHIAKAIIgFAFIABAuIgKAEIAAgKIgxAAIAAAJIgKADIABgjgAgagTIAxAAIAAgnIgxAAg");
		this.shape_21.setTransform(-33.8,-6.5);

		this.shape_22 = new cjs.Shape();
		this.shape_22.graphics.f("#FFFFFF").s().p("AhGBHQAPgTACgXQACgYgBgSIAKAGIA3AAQALgXACgKIAPAIIgGACIgSAXIAgAAIAJgKIANANIhxAAQAAAVgDASQgEATgUATgAgRgQQgCgKgIgLIABgCQAMAIAFAGQAFAGgFAHQgBAAAAABQgBAAAAABQgBAAAAAAQAAAAgBAAQgCAAgCgGgAhBguIBCAAQgBgLgKgOIACgBQALAGAFAGQAFAGgJAIIAsAAIAKgKIANAOIhzAAQgIAAgHABg");
		this.shape_22.setTransform(-50.2,-6.5);

		this.shape_23 = new cjs.Shape();
		this.shape_23.graphics.f("#FFFFFF").s().p("AgWAkQgIgGgDgOIAOgCQABAJAFAEQAGAFAIgBQAJAAAEgEQAGgDgBgGQAAgFgEgDQgCgCgMgCQgOgEgFgCQgHgCgCgFQgEgFAAgGQABgGACgEQACgFAEgDQAEgCAFgCQAHgCAGAAQAHAAAIADQAIADADAEQADAGABAIIgNACQgBgHgFgDQgEgFgHAAQgJAAgEAEQgEADAAAFIACAEIAFAEIALAEQAPADAFAEQAGAAADAEQAEAFAAAIQAAAGgEAHQgFAGgHADQgIAEgJAAQgPAAgIgHg");
		this.shape_23.setTransform(75.9,-24.8);

		this.shape_24 = new cjs.Shape();
		this.shape_24.graphics.f("#FFFFFF").s().p("AgaAfQgLgKAAgVQAAgSALgNQALgLAPAAQARAAAKALQALAMAAATIAAADIg8AAQAAAOAHAHQAHAIAJgBQAHAAAGgEQAFgEAEgIIAOABQgDANgKAHQgJAHgOAAQgRAAgKgMgAgOgYQgHAGgBALIAtAAQgBgKgFgFQgGgJgLAAQgIAAgGAHg");
		this.shape_24.setTransform(67.4,-24.8);

		this.shape_25 = new cjs.Shape();
		this.shape_25.graphics.f("#FFFFFF").s().p("AgVAqIAAhRIAMAAIAAANQAFgJAEgDQACgDAFAAQAIAAAHAFIgFAMQgFgCgFAAQgFAAgCACQgDADgBAFQgDAIAAAIIAAAqg");
		this.shape_25.setTransform(61,-24.9);

		this.shape_26 = new cjs.Shape();
		this.shape_26.graphics.f("#FFFFFF").s().p("AgeAkQgGgHgBgKQAAgGADgFQADgFAEgDQAFgBAGgCIAMgCQAPgCAIgCIAAgFQAAgIgEgDQgGgFgJAAQgJAAgEAEQgFADgDAIIgNgBQABgJAFgGQAEgFAJgDQAHgDAJAAQALAAAHACQAGADADAEQADAEABAFIABAOIAAAQQAAAUABAFIAEAKIgPAAIgDgKQgIAGgHADQgFADgJAAQgNAAgIgHgAgCAEIgMAEQgEABgCADQgBADgBADQAAAGAEAEQAFAEAIAAQAGAAAGgEQAHgDADgHQACgEAAgKIAAgEQgHACgOACg");
		this.shape_26.setTransform(53.2,-24.8);

		this.shape_27 = new cjs.Shape();
		this.shape_27.graphics.f("#FFFFFF").s().p("AgRA0QgIgFgFgKQgEgKAAgNQAAgMADgIQAFgKAIgGQAJgFAJAAQAGAAAGADQAGADADAFIAAgpIAPAAIAABxIgOAAIAAgLQgIANgOAAQgIAAgJgGgAgOgIQgGAIAAAOQAAAQAGAIQAHAIAHAAQAKAAAGgHQAHgIgBgPQABgQgHgHQgGgIgKAAQgHAAgHAHg");
		this.shape_27.setTransform(44,-26.3);

		this.shape_28 = new cjs.Shape();
		this.shape_28.graphics.f("#FFFFFF").s().p("AgeAkQgGgHAAgKQAAgGACgFQADgFAFgDQAEgBAGgCIAMgCQAPgCAIgCIAAgFQAAgIgEgDQgFgFgKAAQgJAAgFAEQgFADgCAIIgNgBQACgJAEgGQAEgFAJgDQAHgDAJAAQALAAAHACQAGADADAEQAEAEAAAFIABAOIAAAQQAAAUABAFIADAKIgOAAIgDgKQgIAGgHADQgFADgIAAQgOAAgIgHgAgCAEIgMAEQgEABgCADQgBADgBADQAAAGAFAEQAEAEAIAAQAGAAAHgEQAGgDADgHQACgEAAgKIAAgEQgHACgOACg");
		this.shape_28.setTransform(35.4,-24.8);

		this.shape_29 = new cjs.Shape();
		this.shape_29.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape_29.setTransform(29.1,-26.4);

		this.shape_30 = new cjs.Shape();
		this.shape_30.graphics.f("#FFFFFF").s().p("AgeAkQgHgHAAgKQABgGACgFQADgFAEgDQAFgBAFgCIANgCQAPgCAIgCIAAgFQAAgIgEgDQgGgFgJAAQgJAAgEAEQgGADgCAIIgOgBQADgJAEgGQAEgFAJgDQAHgDAJAAQALAAAHACQAGADADAEQADAEABAFIABAOIAAAQQAAAUABAFIAEAKIgPAAIgDgKQgHAGgIADQgFADgJAAQgNAAgIgHgAgCAEIgMAEQgEABgCADQgCADABADQAAAGADAEQAFAEAIAAQAGAAAGgEQAHgDADgHQACgEAAgKIAAgEQgHACgOACg");
		this.shape_30.setTransform(22.9,-24.8);

		this.shape_31 = new cjs.Shape();
		this.shape_31.graphics.f("#FFFFFF").s().p("AgHA5IgshxIAQAAIAeBRIAFATIAGgTIAehRIAQAAIgtBxg");
		this.shape_31.setTransform(13.2,-26.4);

		this.shape_32 = new cjs.Shape();
		this.shape_32.graphics.f("#FFFFFF").s().p("AATAqIAAgwQAAgJgBgFQgCgEgEgCQgEgDgGAAQgHAAgHAGQgGAGAAAPIAAAsIgOAAIAAhRIAMAAIAAAMQAKgOAPAAQAHAAAHADQAGACADAFQADAFABAFIABAOIAAAxg");
		this.shape_32.setTransform(-1.1,-24.9);

		this.shape_33 = new cjs.Shape();
		this.shape_33.graphics.f("#FFFFFF").s().p("AgbAfQgKgKAAgVQAAgWAMgLQALgJAOAAQARAAAKALQALALAAAUQAAAOgEAKQgFAIgJAGQgKAFgKAAQgQAAgLgMgAgQgXQgHAJAAAOQAAAPAHAJQAHAHAJAAQAKAAAHgHQAHgIAAgQQAAgPgHgHQgHgIgKAAQgJAAgHAHg");
		this.shape_33.setTransform(-10,-24.8);

		this.shape_34 = new cjs.Shape();
		this.shape_34.graphics.f("#FFFFFF").s().p("AgWAkQgJgGgCgOIAOgCQABAJAFAEQAFAFAIgBQAKAAAFgEQAEgDAAgGQABgFgFgDQgCgCgMgCQgNgEgGgCQgHgCgCgFQgDgFgBgGQAAgGADgEQACgFAEgDQAEgCAFgCQAHgCAGAAQAIAAAHADQAIADADAEQAEAGAAAIIgNACQgBgHgFgDQgEgFgHAAQgJAAgEAEQgEADAAAFIABAEIAGAEIAKAEQAPADAGAEQAGAAADAEQAEAFAAAIQAAAGgEAHQgEAGgIADQgIAEgKAAQgOAAgIgHg");
		this.shape_34.setTransform(-18.4,-24.8);

		this.shape_35 = new cjs.Shape();
		this.shape_35.graphics.f("#FFFFFF").s().p("AgVAqIAAhRIAMAAIAAANQAGgJADgDQADgDAEAAQAIAAAHAFIgFAMQgFgCgFAAQgFAAgCACQgDADgBAFQgDAIAAAIIAAAqg");
		this.shape_35.setTransform(-24.4,-24.9);

		this.shape_36 = new cjs.Shape();
		this.shape_36.graphics.f("#FFFFFF").s().p("AgaAfQgLgKAAgVQAAgSALgNQALgLAPAAQARAAAKALQALAMAAATIAAADIg8AAQAAAOAHAHQAHAIAJgBQAHAAAGgEQAFgEAEgIIAOABQgDANgKAHQgJAHgOAAQgRAAgKgMgAgOgYQgHAGgBALIAtAAQgBgKgFgFQgGgJgLAAQgIAAgGAHg");
		this.shape_36.setTransform(-32.2,-24.8);

		this.shape_37 = new cjs.Shape();
		this.shape_37.graphics.f("#FFFFFF").s().p("AgKA6IAAhGIgNAAIAAgLIANAAIAAgJQAAgIABgEQACgGAFgDQADgEAKAAIANACIgCAMIgJgBQgGAAgDADQgCADAAAHIAAAIIAQAAIAAALIgQAAIAABGg");
		this.shape_37.setTransform(-38.5,-26.5);

		this.shape_38 = new cjs.Shape();
		this.shape_38.graphics.f("#FFFFFF").s().p("AgaAfQgLgKAAgVQAAgSALgNQALgLAPAAQARAAAKALQALAMAAATIAAADIg8AAQAAAOAHAHQAHAIAJgBQAHAAAGgEQAFgEAEgIIAOABQgDANgKAHQgJAHgOAAQgRAAgKgMgAgOgYQgHAGgBALIAtAAQgBgKgFgFQgGgJgLAAQgIAAgGAHg");
		this.shape_38.setTransform(-45.6,-24.8);

		this.shape_39 = new cjs.Shape();
		this.shape_39.graphics.f("#FFFFFF").s().p("AgWAxQgIgJAAgRIAOgCQABAOADAEQAFAFAHAAQAEAAAFgCQAEgDABgEQACgFAAgKIAAhNIAPAAIAABMQAAAPgDAIQgEAHgIAFQgHAEgJAAQgOAAgIgJg");
		this.shape_39.setTransform(-54.4,-26.3);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_39},{t:this.shape_38},{t:this.shape_37},{t:this.shape_36},{t:this.shape_35},{t:this.shape_34},{t:this.shape_33},{t:this.shape_32},{t:this.shape_31},{t:this.shape_30},{t:this.shape_29},{t:this.shape_28},{t:this.shape_27},{t:this.shape_26},{t:this.shape_25},{t:this.shape_24},{t:this.shape_23},{t:this.shape_22},{t:this.shape_21},{t:this.shape_20},{t:this.shape_19},{t:this.shape_18},{t:this.shape_17},{t:this.shape_16},{t:this.shape_15},{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-60,-37.2,142,81.6);


	(lib.t6 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape.setTransform(4.6,13.4);

		this.shape_1 = new cjs.Shape();
		this.shape_1.graphics.f("#FFFFFF").s().p("AgjA6IAAhxIAOAAIAAALQAEgGAFgDQAGgEAHAAQAKAAAIAGQAJAFADAKQAEAKAAAMQABAMgFAJQgEAKgJAFQgJAGgJAAQgFAAgGgDQgGgDgEgEIAAAogAgPgmQgGAJAAAQQAAAOAGAIQAHAHAIABQAIgBAGgHQAHgJAAgOQAAgQgHgIQgGgIgIAAQgIAAgHAIg");
		this.shape_1.setTransform(-1.4,16.5);

		this.shape_2 = new cjs.Shape();
		this.shape_2.graphics.f("#FFFFFF").s().p("AgbAgQgKgLAAgVQAAgWAMgLQALgJAOAAQARAAAKAMQALAKAAAUQAAAOgEAKQgFAIgJAGQgKAFgKAAQgQAAgLgLgAgQgXQgHAJAAAOQAAAPAHAJQAHAHAJABQAKgBAHgHQAHgJAAgPQAAgPgHgHQgHgIgKAAQgJAAgHAHg");
		this.shape_2.setTransform(-10.5,15);

		this.shape_3 = new cjs.Shape();
		this.shape_3.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape_3.setTransform(-16.8,13.4);

		this.shape_4 = new cjs.Shape();
		this.shape_4.graphics.f("#FFFFFF").s().p("AgbAgQgKgLAAgVQAAgWAMgLQALgJAOAAQARAAAKAMQALAKAAAUQAAAOgEAKQgFAIgJAGQgKAFgKAAQgQAAgLgLgAgQgXQgHAJAAAOQAAAPAHAJQAHAHAJABQAKgBAHgHQAHgJAAgPQAAgPgHgHQgHgIgKAAQgJAAgHAHg");
		this.shape_4.setTransform(-23,15);

		this.shape_5 = new cjs.Shape();
		this.shape_5.graphics.f("#FFFFFF").s().p("AgaAzQgLgHgGgPQgGgOAAgPQAAgRAHgOQAGgNAOgHQAMgHANAAQARAAAMAJQALAJAGAQIgQADQgDgMgIgGQgIgGgLAAQgLAAgKAHQgIAGgEALQgEAKAAALQAAANAFALQAEALAJAFQAJAGAIAAQAOAAAIgIQAJgHADgOIAPADQgFATgMAKQgMAKgSAAQgQAAgNgIg");
		this.shape_5.setTransform(-33.1,13.4);

		this.shape_6 = new cjs.Shape();
		this.shape_6.graphics.f("#FFFFFF").s().p("AgoAYIgBg1IAKAGIA3AAIAGgHIAJAJIgFAEIABAZIgKAEIAAgHIg4AAIAAAVIA8AAIAGgGIAJAIIgFAEIABAiIgKAEIAAgIIg9AAIAAAHIgKAEIABgxgAgfA5IA9AAIAAgcIg9AAgAgfACIA4AAIAAgWIg4AAgAhCgXQgEgDAGgGQAGgEAEgPIADAAIAAAGIBlAAIAHgGIALAMQgKAAgMAQIgDgCIAIgQIhmAAQAAANgFAEQgCACgDAAIgFgBgAgDgzQgDgKgJgJIACgBQAMAFADAFQAGAEgFAIQgCADgBAAQgBAAgCgFg");
		this.shape_6.setTransform(33.2,-6.6);

		this.shape_7 = new cjs.Shape();
		this.shape_7.graphics.f("#FFFFFF").s().p("AgpgCQgOANgOAJIgBgBQANgOAMgOQAMgQAHgPIAKAJIgGAEIgNASIAHAFIgEAEIAABCIgKAGIABhKgAgCA5IAAgDIAUABQAGAAAAgHIAAg+IgYAAQgHAAgHACIgFgFIBGAAIAKgJIAKAMIgnAAIAABBQABARgQADQgBgJgSgFgAhBgaQAKgLAKgNQAIgMADgJIAMAJQgEABgLAMQgLAMgQANgAgIg3IAzAAIAJgIIAKAMIg0AAQgIAAgEABg");
		this.shape_7.setTransform(17.1,-6.5);

		this.shape_8 = new cjs.Shape();
		this.shape_8.graphics.f("#FFFFFF").s().p("AghBHQAbgVAIglQgIgGgHgEIAAgCIAQAGQADgKABgWIgNABIgFACIgHgFIAZgCIgCgqIAQAHIgGAEIAAAeIAVgCIAEgHIAKAIIgFAFQgDATACAYQACAbAOAKIAJgcIADABQgCAGgBAMQgBALADAIQAEAHgLgEQgKgEgIgNQgIgLgCgSQgCgSADgeIgWABQAAAWgDAPQAOAHADAEQADAFgCAGQgCAGgFgHQgFgHgIgHQgLAkgfATgAhBA6IAAgCQAPACAFgBQAEAAAAgHIAAgsIgUALIgDADIgHgLQAGgBAYgIIAAgfIgKAAQgIAAgHACIgFgFIAeAAIAAgmIAPAHIgGAEIAAAbIAIAAIAHgIIALALIgaAAIAAAcIAWgJIACADIgYAKIAAAzQABAPgPAFQABgHgUgHg");
		this.shape_8.setTransform(1,-6.5);

		this.shape_9 = new cjs.Shape();
		this.shape_9.graphics.f("#FFFFFF").s().p("AAGATIgaAAIAAAnIgJAEIAAgbIAAgXIAJAEIAaAAIAAgQIgKAAIAAAEIgIACIAAgbIgSAAIgGABIgEgGIAcAAIAAgUIAMAHIgEADIAAAKIAdAAIgBgUIAOAHIgFADIAAAKIAPAAIAIgIIAMANIgjAAIAAAYIgIACIAAgFIgLAAIAAAQIAgAAIAFgGIAJAHIgFAFIAAAXQACAMgOAEQgBgHgPgEIAAgCIAPABQAGAAAAgIIAAgWIgiAAIABAyIgJADIAAg1gAgEgDIAdAAIAAgSIgdAAgAhGBFQAOgfABgmIAAg5IAKAFIAyAAQgBgKgHgJIABgBQAMAFADAFQACAEgFAGIApAAIAJgJIALANIh0AAQAAAogCAYQgDAcgSAag");
		this.shape_9.setTransform(-15.1,-6.5);

		this.shape_10 = new cjs.Shape();
		this.shape_10.graphics.f("#FFFFFF").s().p("AgoAbQAAgjgBgNIAJAFIASAAQAEgKABgLIgrAAQgGAAgHACIgFgFIAzAAIgEgNQgCgGgHgIIAAgCQANAHAHAGQAGAHgIAJIAYAAQAIgQAEgQIAOAJIgHACIgQAVIAjAAIAKgJIANAMIhFAAQgGAIgGANIAoAAIAFgHIALAHIgGAFIAAA8IABASIgKAFIAAgNIg/AAIAAAKIgJADQABgHAAgmgAggA2IA/AAIAAgVIg/AAgAggAeIA/AAIAAgVIg/AAgAggAGIA/AAIAAgSIg/AAg");
		this.shape_10.setTransform(-30.9,-6.5);

		this.shape_11 = new cjs.Shape();
		this.shape_11.graphics.f("#FFFFFF").s().p("AgXA0QgKgGABgOIANACQABAGAEADQAFAEAJAAQAIAAAGgEQAFgEACgHQABgEAAgOQgJALgNAAQgQAAgKgMQgJgMAAgQQAAgMAEgKQAFgKAIgFQAIgGAKAAQAOAAAJAMIAAgKIANAAIAABGQAAATgEAJQgEAIgIAEQgJAFgLAAQgOAAgJgHgAgOgmQgHAHAAAPQAAAQAHAGQAGAIAIAAQAKAAAGgIQAHgGAAgPQAAgPgHgIQgHgIgJAAQgIAAgGAIg");
		this.shape_11.setTransform(41.7,-23.3);

		this.shape_12 = new cjs.Shape();
		this.shape_12.graphics.f("#FFFFFF").s().p("AATAqIAAgwQAAgJgBgFQgCgEgEgCQgEgDgGAAQgHAAgHAGQgGAGAAAPIAAAsIgOAAIAAhRIAMAAIAAAMQAKgOAPAAQAHAAAHADQAGACADAFQADAFABAFIABAOIAAAxg");
		this.shape_12.setTransform(33.1,-24.9);

		this.shape_13 = new cjs.Shape();
		this.shape_13.graphics.f("#FFFFFF").s().p("AgRAnQgHgDgDgEQgDgEgBgHIgBgNIAAgxIAOAAIAAAsIABAPQABAGAFACQAEAEAGAAQAEAAAGgEQAGgDACgGQACgFAAgLIAAgqIAOAAIAABRIgMAAIAAgMQgKAOgPAAQgHAAgGgDg");
		this.shape_13.setTransform(24.1,-24.7);

		this.shape_14 = new cjs.Shape();
		this.shape_14.graphics.f("#FFFFFF").s().p("AgWAxQgIgJAAgRIAOgCQABAOADAEQAFAFAHAAQAEAAAFgCQAEgDABgEQACgFAAgKIAAhNIAPAAIAABMQAAAPgDAIQgEAHgIAFQgHAEgJAAQgOAAgIgJg");
		this.shape_14.setTransform(15.4,-26.3);

		this.shape_15 = new cjs.Shape();
		this.shape_15.graphics.f("#FFFFFF").s().p("AATAqIAAgwQAAgJgBgFQgCgEgEgCQgEgDgGAAQgHAAgHAGQgGAGAAAPIAAAsIgOAAIAAhRIAMAAIAAAMQAKgOAPAAQAHAAAHADQAGACADAFQADAFABAFIABAOIAAAxg");
		this.shape_15.setTransform(2.8,-24.9);

		this.shape_16 = new cjs.Shape();
		this.shape_16.graphics.f("#FFFFFF").s().p("AgdAkQgIgHAAgKQABgGACgFQADgFAEgDQAFgBAFgCIANgCQAPgCAIgCIAAgFQAAgIgEgDQgGgFgJAAQgJAAgEAEQgGADgBAIIgPgBQACgJAFgGQAEgFAIgDQAJgDAIAAQALAAAGACQAHADADAEQADAEACAFIAAAOIAAAQQAAAUABAFIADAKIgOAAIgDgKQgIAGgHADQgFADgJAAQgNAAgHgHgAgCAEIgMAEQgEABgCADQgBADAAADQAAAGADAEQAFAEAIAAQAGAAAGgEQAHgDADgHQACgEAAgKIAAgEQgIACgNACg");
		this.shape_16.setTransform(-6.1,-24.8);

		this.shape_17 = new cjs.Shape();
		this.shape_17.graphics.f("#FFFFFF").s().p("AATA5IAAg1QAAgIgEgFQgFgFgIAAQgEAAgGADQgFAEgDAFQgCAEAAAJIAAAuIgOAAIAAhxIAOAAIAAApQAKgLANAAQAJAAAHAEQAHADACAHQADAGAAAKIAAA1g");
		this.shape_17.setTransform(-15,-26.4);

		this.shape_18 = new cjs.Shape();
		this.shape_18.graphics.f("#FFFFFF").s().p("AAQA5IgZgrIgKAKIAAAhIgOAAIAAhxIAOAAIAABAIAfggIASAAIgeAdIAiA0g");
		this.shape_18.setTransform(-22.9,-26.4);

		this.shape_19 = new cjs.Shape();
		this.shape_19.graphics.f("#FFFFFF").s().p("AgFA5IAAhRIALAAIAABRgAgFgoIAAgQIALAAIAAAQg");
		this.shape_19.setTransform(-29.2,-26.4);

		this.shape_20 = new cjs.Shape();
		this.shape_20.graphics.f("#FFFFFF").s().p("AgWAxQgIgJAAgRIAOgCQAAAOAFAEQAEAFAHAAQAEAAAEgCQAFgDACgEQABgFAAgKIAAhNIAPAAIAABMQAAAPgEAIQgDAHgIAFQgHAEgJAAQgOAAgIgJg");
		this.shape_20.setTransform(-35.3,-26.3);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_20},{t:this.shape_19},{t:this.shape_18},{t:this.shape_17},{t:this.shape_16},{t:this.shape_15},{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-40.9,-37.2,142,61.7);


	(lib.t5 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("#FFFFFF").s().p("AAjA5IgMgjIguAAIgNAjIgQAAIAshxIAOAAIAvBxgAgHgWIgMAgIAkAAIgMgeIgGgYQgBALgFALg");
		this.shape.setTransform(-42,13.4);

		this.shape_1 = new cjs.Shape();
		this.shape_1.graphics.f("#FFFFFF").s().p("AgpA5IAAhxIBRAAIAAAOIhCAAIAAAjIA9AAIAAALIg9AAIAAAnIBEAAIAAAOg");
		this.shape_1.setTransform(-52.5,13.4);

		this.shape_2 = new cjs.Shape();
		this.shape_2.graphics.f("#FFFFFF").s().p("AgOBFQAYgMARgTQgHgNgCgPIgDgbIhCAAQgIAAgHACIgFgGIApAAIAAgUIgMAAQgIAAgHACIgGgFIAhAAIgBgbIAPAIIgGAEIAAAPIANAAIAHgHIAJAKIgdAAIAAAUIAlAAIgBgyIAPAHIgFAEIAAAnIAcAAIAIgJIAMANIgwAAQABAfAHAQQANgTAHgQIAMAHIgGACQgMAVgKANQALANANAHIAIgcIACABQgCAQAAAJQABAJABAEQACAEgGgBQgGgCgKgHQgKgHgJgMQgTATgZAJgAgtA6QACgEAAgDIAAgWQgKAMgQAJIgCgCQAZgTALgYIgNAAQgIAAgHACIgGgFIBBAAIAGgGIAKAJIgmAAIgIAPIAAAiIAXgKIABACQgXAQgFAFgAgFAtQgIgLgRgNIABgBIAPAIQAKgKAEgJIAIAJIgIADIgMAIQAKAGADAEQAFAFgDAGQAAABAAAAQgBABAAAAQAAAAgBAAQAAABAAAAQgBAAgFgIgAggAAQgCgFgIgKIACgBIANAIQAEADgDAFQgBAAAAAAQgBAAAAAAQgBABAAAAQAAAAgBAAQAAAAAAAAQgBAAAAgBQAAAAAAAAQgBAAAAAAgAAtgnQgDgJgIgKIABgBQAOAJAEAEQAEAFgEAGQgBAAAAAAQAAABgBAAQAAAAAAAAQgBABAAAAQgDAAgCgGg");
		this.shape_2.setTransform(46,-6.6);

		this.shape_3 = new cjs.Shape();
		this.shape_3.graphics.f("#FFFFFF").s().p("AgQBFQgOAAABgNIAAghIAOAIIgFAEIAAATQAAAHAGAAIAlAAQAGAAABgGQACgHAAgNIADAAIABASQABAFAGACQgEAJgKAAgAhBA6QgDgCAIgHQAIgGAFgPIACAAQgBAUgDAFQgDAGgFABIgCAAQgDAAgDgCgAA5AzQgFgKgOgNIABgCQAMAFAHAFQAIAEABAFQABAFgDAFQgBABAAAAQAAABgBAAQAAAAAAAAQgBAAAAAAQgDAAgCgGgAADAmQgDgJgLgNIACgBQANAHAFAFQAGAFgEAHQgBABAAABQgBAAAAABQgBAAAAAAQgBAAAAAAQgCAAgCgEgAgngHIAAgcIAJAFIAlAAQALgUAFgSIANAKIgGABIgTAbIAZAAIAEgGIAKAIIgFAEQAAAdABAKIgKAEIAAgHIhCAAIAAAFIgJADIAAgbgAgeAIIBCAAIAAgiIhCAAgAgSgpQgBgJgJgNIACgCQAKAHAFAFQAFAFgBAFQgCAFgDADIgCABQgDAAgBgHg");
		this.shape_3.setTransform(29.8,-6.9);

		this.shape_4 = new cjs.Shape();
		this.shape_4.graphics.f("#FFFFFF").s().p("AAgA6IAAgCIAUABQABAAAAAAQABgBAAAAQAAgBAAAAQAAgBAAgBIAAhjIAAgZIAOAHIgFAEIAABzQAAAHgDADQgCAEgHADQgDgJgQgFgAg+AiIAAgiIAJACIA2AAIAFgDIAJAGIgEADIAAApIAAANIgJAFIAAgKIg4AAIAAAKIgIADIAAgkgAgVA1IAXAAIAAgXIgXAAgAg2A1IAaAAIAAgXIgaAAgAgVAaIAXAAIAAgUIgXAAgAg2AaIAaAAIAAgUIgaAAgAAYgJIAAgoIAPAIIgGADIAAAwIAAASIgJAFIAAgqgAgzgaIgBgVIAJAFIAlAAIAGgGIAGAIIgFAEIAAALIABAOIgGADIAAgHIgnAAIAAAHIgJADIABgVgAgrgSIAnAAIAAgUIgnAAgAhDg7IBDAAIAFgHIALALIg+AAQgLAAgFACg");
		this.shape_4.setTransform(13.8,-6.4);

		this.shape_5 = new cjs.Shape();
		this.shape_5.graphics.f("#FFFFFF").s().p("AgpgCQgOANgOAJIgBgBQANgOAMgOQANgQAFgPIALAJIgFAEIgOASIAGAFIgDAEIAABCIgJAGIAAhKgAgCA5IAAgDIAUABQAFAAAAgHIAAg+IgXAAQgGAAgHACIgGgFIBGAAIAJgJIALAMIgnAAIAABBQABARgRADQAAgJgSgFgAhBgaQAKgLAJgNQAKgMACgJIAMAJQgFABgKAMQgKAMgRANgAgJg3IA0AAIAIgIIALAMIgzAAQgJAAgEABg");
		this.shape_5.setTransform(-1.9,-6.5);

		this.shape_6 = new cjs.Shape();
		this.shape_6.graphics.f("#FFFFFF").s().p("AgvBHQAmgNAQgUQgMgSgHgUIgGAAQgHATgMASQgMASgVAOIgBgCQALgJAJgNQAJgNAIgRQAHgPAGgbIgdAAIgFAGIgIgJIAFgDQAJgUACgLIAMAHIgFAEIgKAWIAeAAIACgVIADgUIANAGIgFAEIgFAfIA9AAIAJgIIALAMIhRAAIgGAXIA0AAIAEgHIALAKIgGACQgLAVgOAQQAVARAgAGIAAACQgIACgDAHQgegLgRgRQgXAVglAIgAANAgQALgNAKgTIgrAAQAJASANAOgAAigsQgFgJgPgOIABgCQASAIAHAFQAGAFgDAIQgBAEgCAAQgDAAgDgFg");
		this.shape_6.setTransform(-17.9,-6.6);

		this.shape_7 = new cjs.Shape();
		this.shape_7.graphics.f("#FFFFFF").s().p("AgzBHQAQgTAIgWQAHgXAAgmIAAAAIAAAAIgGACIgFgHIAdAAIAEgGIAJALIgWAAIgCAWIANAAIAFgGIAIAIIgFAEQgDA2gFAJQgDAHgIACQAAgIgNgGIABgCQAKAEAGgBQAFgBACgXIABgmIgOAAQgCAegKASQgJARgQANgAASA8IAAgCIAOABQAFAAAAgFIAAglIgGAAQgIAAgHACIgGgFIAbAAIgBgUIAEADIANgQIgQAAQgIAAgHACIgFgGIAkAAIAHgGIAJAKQgHABgHAGIgMAKIAGACIgEABIAAANIAKAAIAIgIIAJALIgbAAIAAAmQAAAOgMACQAAgHgPgEgAg4A8QgHgDADgMQAEgMgDgDQgEgDgIgDIAAgCIAMABQADgBAEgGQADgEAQgzIACAAIgQBAQgCAHAAAMIAAAQQAAAAgBABQAAAAAAAAQAAABgBAAQAAAAgBAAIgEgCgAg8gLQgCgJgJgMIABgCQAUALAAAGQAAAHgEADIgCABQgCAAgCgFgAAHgTQAKgQAFgOQAFgOABgJIANAIIgGADIgKATIAcAAIAJgHIAJALIgwAAQgHALgIAJgAgvguQgDgIgJgMIABgBQARAIACAEQADAFgEAGQAAABgBAAQAAAAAAABQgBAAAAAAQAAAAgBAAQgCAAgCgEgAgNguQgCgJgJgLIABgCQANAIAEAFQAFAFgFAFQgBABAAABQgBAAAAAAQgBABAAAAQAAAAgBAAQgCAAgBgEg");
		this.shape_7.setTransform(-34.1,-6.5);

		this.shape_8 = new cjs.Shape();
		this.shape_8.graphics.f("#FFFFFF").s().p("AgfA5IAAgDIATABQAIABAAgHIAAgmIgvAAQgIAAgHACIgGgGIBEAAIAAgbIggAAQgIAAgHACIgGgGIA1AAIAAgcIg3ACIAAgDQApgCAZgFQAbgFAKgFIALANQgPAAglAEIAAAdIAgAAIAJgJIANANIg2AAIAAAbIAuAAIAKgIIAOAMIhGAAIAAAqQAAAOgOAEQAAgIgUgGg");
		this.shape_8.setTransform(-50,-6.4);

		this.shape_9 = new cjs.Shape();
		this.shape_9.graphics.f("#FFFFFF").s().p("AATA5IAAg1QAAgIgEgFQgFgFgIAAQgEAAgGADQgFAEgDAFQgCAEAAAJIAAAuIgOAAIAAhxIAOAAIAAApQAKgLANAAQAJAAAHAEQAHADACAHQADAGAAAKIAAA1g");
		this.shape_9.setTransform(5.1,-26.4);

		this.shape_10 = new cjs.Shape();
		this.shape_10.graphics.f("#FFFFFF").s().p("AgWAkQgJgGgCgOIAOgCQABAJAGAEQAFAFAHgBQAKAAAFgEQAEgDAAgGQABgFgFgDQgCgCgMgCQgOgEgGgCQgFgCgEgFQgCgFAAgGQAAgGACgEQADgFAEgDQADgCAGgCQAFgCAHAAQAHAAAIADQAHADAEAEQAEAGABAIIgOACQgBgHgEgDQgFgFgHAAQgJAAgEAEQgEADAAAFIABAEIAGAEIAKAEQAPADAHAEQAFAAAEAEQADAFAAAIQAAAGgEAHQgFAGgHADQgIAEgKAAQgOAAgIgHg");
		this.shape_10.setTransform(-3.4,-24.8);

		this.shape_11 = new cjs.Shape();
		this.shape_11.graphics.f("#FFFFFF").s().p("AgFA5IAAhRIALAAIAABRgAgFgoIAAgQIALAAIAAAQg");
		this.shape_11.setTransform(-9.1,-26.4);

		this.shape_12 = new cjs.Shape();
		this.shape_12.graphics.f("#FFFFFF").s().p("AAfA5IgPgYIgLgQIgGgIIgGgDIgJAAIgSAAIAAAzIgPAAIAAhxIAxAAQAPAAAIADQAIADAFAIQAEAIAAAJQAAANgHAIQgIAGgQADIAJAFQAGAGAGAKIAUAfgAgigFIAhAAQAIAAAGgCQAGgDADgEQADgFAAgGQAAgIgGgFQgGgFgMAAIgjAAg");
		this.shape_12.setTransform(-16.1,-26.4);

		this.shape_13 = new cjs.Shape();
		this.shape_13.graphics.f("#FFFFFF").s().p("AAQA5IgZgrIgKAKIAAAhIgOAAIAAhxIAOAAIAABAIAgggIASAAIgfAdIAiA0g");
		this.shape_13.setTransform(-30.4,-26.4);

		this.shape_14 = new cjs.Shape();
		this.shape_14.graphics.f("#FFFFFF").s().p("AgYAgQgKgLAAgVQAAgMADgKQAFgKAKgFQAIgFAJAAQANAAAJAHQAJAHACAMIgNACQgDgIgFgEQgFgFgGAAQgJABgGAHQgIAIAAAPQABAQAGAIQAGAHAJAAQAIABAFgGQAGgFABgLIAPACQgDAPgJAIQgKAIgNAAQgPAAgKgLg");
		this.shape_14.setTransform(-38.6,-24.8);

		this.shape_15 = new cjs.Shape();
		this.shape_15.graphics.f("#FFFFFF").s().p("AgFA5IAAhRIALAAIAABRgAgFgoIAAgQIALAAIAAAQg");
		this.shape_15.setTransform(-44.7,-26.4);

		this.shape_16 = new cjs.Shape();
		this.shape_16.graphics.f("#FFFFFF").s().p("AAdA5Ig6hYIAABYIgPAAIAAhxIAQAAIA6BYIAAhYIAPAAIAABxg");
		this.shape_16.setTransform(-52.3,-26.4);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15},{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-60,-37.2,142,61.7);


	(lib.t4 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.text = new cjs.Text("Michael Chang\n企业发展高级副总裁\nNCsoft", "16px 'Arial'", "#FFFFFF");
		this.text.lineHeight = 20;
		this.text.lineWidth = 201;
		this.text.setTransform(-60,-37.2);

		this.timeline.addTween(cjs.Tween.get(this.text).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-60,-37.2,205,79.7);


	(lib.t3 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("#FFFFFF").s().p("AgRA0QgJgFgEgKQgEgKgBgNQAAgMAEgIQAFgKAIgGQAIgFAKAAQAGAAAGADQAGADADAFIAAgpIAOAAIAABxIgMAAIAAgLQgIANgPAAQgIAAgJgGgAgOgIQgGAIAAAOQAAAQAGAIQAHAIAHAAQAKAAAGgHQAHgIAAgPQAAgQgHgHQgHgIgJAAQgHAAgHAHg");
		this.shape.setTransform(-5.8,13.5);

		this.shape_1 = new cjs.Shape();
		this.shape_1.graphics.f("#FFFFFF").s().p("AgVAqIAAhRIAMAAIAAANQAFgJAEgDQADgDAEAAQAIAAAHAFIgFAMQgFgCgFAAQgFAAgCACQgDADgCAFQgCAHAAAJIAAAqg");
		this.shape_1.setTransform(-12,14.9);

		this.shape_2 = new cjs.Shape();
		this.shape_2.graphics.f("#FFFFFF").s().p("AgeAkQgHgGAAgLQABgGACgFQADgFAEgDQAFgBAFgBIANgDQAPgCAIgCIAAgFQAAgIgEgEQgGgEgJAAQgJAAgEADQgGAEgCAIIgOgCQADgIAEgFQAEgGAJgDQAHgDAJAAQALAAAHADQAGACADAEQADAEABAFIABAOIAAAQQAAAUABAFIAEAKIgPAAIgDgKQgHAHgIACQgFADgJAAQgNAAgIgHgAgCAFIgMADQgEABgCADQgCADABADQAAAHADADQAFAEAIAAQAGAAAGgEQAHgDADgHQACgEAAgJIAAgFQgHADgOACg");
		this.shape_2.setTransform(-19.8,15);

		this.shape_3 = new cjs.Shape();
		this.shape_3.graphics.f("#FFFFFF").s().p("AgjApIAAgMIAzg6IgQABIggAAIAAgMIBCAAIAAAKIgrAyIgJAJIASAAIAkAAIAAAMg");
		this.shape_3.setTransform(-28.3,15);

		this.shape_4 = new cjs.Shape();
		this.shape_4.graphics.f("#FFFFFF").s().p("AgjApIAAgMIAzg6IgQABIggAAIAAgMIBCAAIAAAKIgrAyIgJAJIASAAIAkAAIAAAMg");
		this.shape_4.setTransform(-36.3,15);

		this.shape_5 = new cjs.Shape();
		this.shape_5.graphics.f("#FFFFFF").s().p("AgGA5IAAhRIANAAIAABRgAgGgoIAAgQIANAAIAAAQg");
		this.shape_5.setTransform(-42,13.4);

		this.shape_6 = new cjs.Shape();
		this.shape_6.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape_6.setTransform(-45.6,13.4);

		this.shape_7 = new cjs.Shape();
		this.shape_7.graphics.f("#FFFFFF").s().p("AgqA5IAAhxIAqAAQANAAAIAEQAHADAFAHQAEAIAAAIQAAAHgEAGQgEAHgIAEQALACAFAGQAGAIAAAKQAAAIgDAHQgEAGgFAEQgFAEgHACQgIACgLAAgAgaArIAaAAIAKAAQAGgBADgCQAEgDACgEQACgEAAgFQAAgHgDgFQgDgEgGgCQgGgCgJAAIgaAAgAgagIIAYAAIANgBQAGgCADgEQACgEAAgGQAAgFgCgFQgDgEgFgCQgFgBgKAAIgXAAg");
		this.shape_7.setTransform(-52.5,13.4);

		this.shape_8 = new cjs.Shape();
		this.shape_8.graphics.f("#FFFFFF").s().p("AhHA/IAUAAIgBgzIAJAGIBQAAIAHgGIAJAIIgGAFIAAAmIAHAAIAIgIIAKAMIh7AAQgIAAgHACgAARA/IAWAAIAAgpIgWAAgAgMA/IAVAAIAAgpIgVAAgAgrA/IAWAAIAAgpIgWAAgAgYgaIgBgqIAOAHIgFAFIAABBIgJAFIABgogAAnAAQgEgJgEgGIgJgMIACgCQARAMAFAIQAFAIgEAEQAAAAgBABQAAAAgBAAQAAABAAAAQgBAAAAAAQgDAAgCgFgAg1geIgBgdIANAGIgEAFIABAwIgKAEIABgigAgJAAQAJgSAFgQQAFgQADgSIAOAJIgGAEIgEAOIAdAAIAIgIIALAMIgyAAQgMAagKAMg");
		this.shape_8.setTransform(62,-6.8);

		this.shape_9 = new cjs.Shape();
		this.shape_9.graphics.f("#FFFFFF").s().p("AgQBFQgOAAABgNIAAghIAOAIIgFAEIAAATQAAAHAGAAIAlAAQAGAAABgGQACgHAAgNIADAAIABASQABAFAGACQgEAJgKAAgAhBA6QgDgCAIgHQAIgGAFgPIACAAQgBAUgDAFQgDAGgFABIgCAAQgDAAgDgCgAA5AzQgFgKgOgNIABgCQAMAFAHAFQAIAEABAFQABAFgDAFQgBABAAAAQAAABgBAAQAAAAAAAAQgBAAAAAAQgDAAgCgGgAADAmQgDgJgLgNIACgBQANAHAFAFQAGAFgEAHQgBABAAABQgBAAAAABQgBAAAAAAQgBAAAAAAQgCAAgCgEgAgngHIAAgcIAJAFIAlAAQALgUAFgSIANAKIgGABIgTAbIAZAAIAEgGIAKAIIgFAEQAAAdABAKIgKAEIAAgHIhCAAIAAAFIgJADIAAgbgAgeAIIBCAAIAAgiIhCAAgAgSgpQgBgJgJgNIACgCQAKAHAFAFQAFAFgBAFQgCAFgDADIgCABQgDAAgBgHg");
		this.shape_9.setTransform(45.8,-6.9);

		this.shape_10 = new cjs.Shape();
		this.shape_10.graphics.f("#FFFFFF").s().p("AgEA6IAAgDIAPACQAGAAAAgDIAAgmQgdAYgCAGIgKgKQAJgCAggWIAAgsIgUAAQgHgBgGACIgFgFIAmAAIgBgjIAOAGIgFAEIAAAZIAbAAIAIgIIAMAMIgvAAQAFAVAHANQAOgNAEgKIAMAKQgHAAgVAQQAOAbAUAIIAAADQgJgBgFAHQgKgKgKgTQgKgTgEgUIAABKQAAANgPAEQAAgJgOgFgAhIApIAYgHIAAglIgBAAQgIAAgHACIgFgGIAVAAIAAgnIgDAAQgIAAgHACIgFgFIApAAIAHgHIAKAKIgaAAIAAAnIAIAAIAHgGIAIAKIgXAAIAAAiIAagJIAAADQgwAUgEAHgAgCAAQgEgJgJgKIABgCQAOAIAEAFQAEAFgEAGQgBAAAAABQgBABAAAAQAAAAgBAAQAAABgBAAQAAAAgCgGgAAxgvQgEgIgIgJIABgCQAOAGADAEQAEAEgEAHQAAAAgBABQAAABAAAAQgBAAAAAAQAAABgBAAQgCAAgBgFg");
		this.shape_10.setTransform(30,-6.6);

		this.shape_11 = new cjs.Shape();
		this.shape_11.graphics.f("#FFFFFF").s().p("AhBBAIA+AAIAAgkIgXAAQgIAAgHACIgFgFIArAAIAAgdIgUAAQgIAAgGACIgFgEQgNAIgQAGIAAgCQAYgMAQgUQAQgUAJgXIAMAIIgGADQAJANASAQQASAQAbAEIAAADQgJABgCAFQgOgDgOgLQgVgRgMgZQgTAfgVARIBCAAIAHgGIAKAKIgnAAIAAAdIAaAAIAHgIIAMALIgtAAIAAAkIArAAIAIgJIAOANIhyAAQgIAAgHACgAgogIQAVgRATgfQAMAZAVARIgHAGgAAhgOIAAAAg");
		this.shape_11.setTransform(14,-6.9);

		this.shape_12 = new cjs.Shape();
		this.shape_12.graphics.f("#FFFFFF").s().p("AhHBFQALgSAFgRQAEgRAAgSIABglIgBgeIAKAFIBVAAIAFgHIAKAIIgFAFIAAAZIgJADIAAgIIhWAAIAAAZIAcAAIAAgXIANAHIgFAEIAAAMIAYAAIAAgWIAOAGIgGAEIAAAMIANAAIAHgIIALALIgfAAIAAATIAVAAIAJgJIAMANIhBAAQAGAOAJAKQAQgMAIgKIAIAKQgGAAgXAOQAKAIAMADQANAEAOABIAAACQgLADgCAHQgRgEgRgNQgQgMgIgZIgOAAIAAAqIAXgLIABACQgUAOgIAJIgHgKQAEgCgBgHIAAglIgVAAIAAgEIAeAAIAAgTIgHAAIgWAAIABgDIgBAAIAAADIgBAOIAAAFIgBAEIABAAQgCALgDALQgFARgQARgAgFAKIAYAAIAAgTIgYAAgAgpgoIBWAAIAAgTIhWAAgAgrAOgAgsAOIABgEIAAAEgAgrAKIAAgFIABgOIAWAAIAHAAIAAATgAgrAKgAgqgJgAgqgMIABAAIgBADg");
		this.shape_12.setTransform(-2,-6.4);

		this.shape_13 = new cjs.Shape();
		this.shape_13.graphics.f("#FFFFFF").s().p("AgvBHQAmgNAQgUQgMgSgHgUIgGAAQgHATgMASQgMASgVAOIgBgCQALgJAJgNQAJgNAIgRQAHgPAGgbIgdAAIgFAGIgIgJIAFgDQAJgUACgLIAMAHIgFAEIgKAWIAeAAIACgVIADgUIANAGIgFAEIgFAfIA9AAIAJgIIALAMIhRAAIgGAXIA0AAIAEgHIALAKIgGACQgLAVgOAQQAVARAgAGIAAACQgIACgDAHQgegLgRgRQgXAVglAIgAANAgQALgNAKgTIgrAAQAJASANAOgAAigsQgFgJgPgOIABgCQASAIAHAFQAGAFgDAIQgBAEgCAAQgDAAgDgFg");
		this.shape_13.setTransform(-17.9,-6.6);

		this.shape_14 = new cjs.Shape();
		this.shape_14.graphics.f("#FFFFFF").s().p("Ag/A9IAEgDIAAhmIAAgUIAJAFIBbAAIAJgJIAMANIhwAAIAABzIBeAAIAIgJIAMAMIhxAAIgEAGgAgnAsIAVgUQAKgKAIgOQgOgTgSgRIACgBQARAMASATQAJgKAFgLIAKgSIAOAKQgGABgIAMIgRAWQAXAUACAHQADAHgDAHQgDAGgFgJQgEgJgGgHIgMgQQgLAOgMAKQgMAKgKAGg");
		this.shape_14.setTransform(-33.8,-6.5);

		this.shape_15 = new cjs.Shape();
		this.shape_15.graphics.f("#FFFFFF").s().p("AgqADQgNAQgPAJIgBgBQANgNALgPQAMgQAIgQIgWAAQgIAAgHACIgGgGIArAAIAFgHIALAKIgGADIgQAZQARAGAEADQADAFgDAGQgEAGgEgIQgEgJgJgGQAAA7ABAMIgKAEIAAhFgAgXA1IApAAIAAg/IgMAAQgHAAgGACIgGgGIAfAAIgBg4IAQAHIgGAEIAAAtIAUAAIAIgIIALAMIgnAAIAAA/IAZAAIAJgJIALANIhKAAQgJAAgGABgAgmgzQgDgJgIgJIACgCQAPAIACAFQADAFgFAGQAAAAgBABQAAAAAAAAQgBABAAAAQgBAAAAAAQgCAAgBgGg");
		this.shape_15.setTransform(-50,-6.4);

		this.shape_16 = new cjs.Shape();
		this.shape_16.graphics.f("#FFFFFF").s().p("AATA5IAAg1QAAgIgEgFQgFgFgIAAQgEAAgGADQgFAEgDAFQgCAEAAAJIAAAuIgOAAIAAhxIAOAAIAAApQAKgLANAAQAJAAAHAEQAHADACAHQADAGAAAKIAAA1g");
		this.shape_16.setTransform(37.1,-26.4);

		this.shape_17 = new cjs.Shape();
		this.shape_17.graphics.f("#FFFFFF").s().p("AgBA1QgEgCgCgDQgCgFAAgMIAAguIgKAAIAAgLIAKAAIAAgVIAMgIIAAAdIAPAAIAAALIgPAAIAAAvQAAAFABACQAAAAABABQAAAAAAABQAAAAABAAQAAABABAAIAEABIAHgBIACANIgLABQgIAAgCgDg");
		this.shape_17.setTransform(30.6,-26.2);

		this.shape_18 = new cjs.Shape();
		this.shape_18.graphics.f("#FFFFFF").s().p("AgGA5IAAhRIANAAIAABRgAgGgoIAAgQIANAAIAAAQg");
		this.shape_18.setTransform(26.5,-26.4);

		this.shape_19 = new cjs.Shape();
		this.shape_19.graphics.f("#FFFFFF").s().p("AAqAqIAAgyQAAgIgCgFQgBgDgEgCQgDgCgFgBQgJAAgGAGQgFAGAAANIAAAuIgMAAIAAg0QAAgJgEgFQgDgFgIAAQgGABgFADQgFACgCAHQgDAFAAALIAAAqIgOAAIAAhRIANAAIAAAMQAEgHAGgDQAHgEAIAAQAJAAAGAEQAEAEACAHQAKgPAQAAQAMAAAHAHQAHAHAAAOIAAA3g");
		this.shape_19.setTransform(18,-24.9);

		this.shape_20 = new cjs.Shape();
		this.shape_20.graphics.f("#FFFFFF").s().p("AgVA2QgLgEgGgJQgGgKAAgLIAOgBQABAIAEAGQAEAFAHAEQAIADAIAAQAJAAAGgCQAHgDADgEQAEgFAAgFQAAgGgEgEQgDgEgHgDIgSgFQgQgEgGgBQgJgFgEgGQgEgGAAgIQAAgJAFgHQAFgIAKgEQAJgEALAAQALAAAKAEQAKAEAFAIQAFAIABAKIgPACQgBgLgHgGQgHgFgMAAQgMAAgHAFQgGAFAAAHQAAAGAEAEQAFAEAQAEQATAEAHADQAKADAEAHQAFAHAAAJQAAAKgFAIQgFAIgKAEQgKAFgMAAQgOAAgKgFg");
		this.shape_20.setTransform(6,-26.4);

		this.shape_21 = new cjs.Shape();
		this.shape_21.graphics.f("#FFFFFF").s().p("AATAqIAAgwQAAgJgBgFQgCgEgEgCQgEgDgGAAQgHAAgHAGQgGAGAAAPIAAAsIgOAAIAAhRIAMAAIAAAMQAKgOAPAAQAHAAAHADQAGACADAFQADAFABAFIABAOIAAAxg");
		this.shape_21.setTransform(-8.2,-24.9);

		this.shape_22 = new cjs.Shape();
		this.shape_22.graphics.f("#FFFFFF").s().p("AgdA4IgCgNIAJABQAEAAADgBIAEgFIAFgKIABgEIgghRIAPAAIASAwIAEASIAGgRIARgxIAPAAIggBTQgFANgBAFQgDAHgGAEQgFADgGAAIgJgCg");
		this.shape_22.setTransform(-16.6,-23.2);

		this.shape_23 = new cjs.Shape();
		this.shape_23.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape_23.setTransform(-22.5,-26.4);

		this.shape_24 = new cjs.Shape();
		this.shape_24.graphics.f("#FFFFFF").s().p("AgdAkQgIgHABgKQgBgGADgFQADgFAEgDQAFgBAFgCIANgCQAPgCAIgCIAAgFQAAgIgEgDQgGgFgJAAQgJAAgFAEQgEADgCAIIgPgBQACgJAFgGQAEgFAIgDQAJgDAIAAQALAAAGACQAHADADAEQAEAEABAFIAAAOIAAAQQAAAUABAFIADAKIgOAAIgDgKQgIAGgHADQgFADgJAAQgNAAgHgHgAgCAEIgMAEQgEABgCADQgBADAAADQAAAGADAEQAFAEAIAAQAGAAAGgEQAHgDADgHQACgEAAgKIAAgEQgIACgNACg");
		this.shape_24.setTransform(-28.7,-24.8);

		this.shape_25 = new cjs.Shape();
		this.shape_25.graphics.f("#FFFFFF").s().p("AgVAqIAAhRIAMAAIAAANQAGgJADgDQADgDAEAAQAIAAAHAFIgFAMQgFgCgFAAQgFAAgCACQgDADgBAFQgDAIAAAIIAAAqg");
		this.shape_25.setTransform(-35.1,-24.9);

		this.shape_26 = new cjs.Shape();
		this.shape_26.graphics.f("#FFFFFF").s().p("AgeAkQgGgHAAgKQAAgGACgFQADgFAFgDQAEgBAGgCIAMgCQAPgCAIgCIAAgFQAAgIgEgDQgFgFgKAAQgJAAgFAEQgFADgCAIIgNgBQACgJAEgGQAEgFAJgDQAHgDAJAAQALAAAHACQAGADADAEQAEAEAAAFIABAOIAAAQQAAAUABAFIADAKIgOAAIgDgKQgIAGgHADQgFADgIAAQgOAAgIgHgAgCAEIgMAEQgEABgCADQgBADgBADQAAAGAFAEQAEAEAIAAQAGAAAHgEQAGgDADgHQACgEAAgKIAAgEQgHACgOACg");
		this.shape_26.setTransform(-42.9,-24.8);

		this.shape_27 = new cjs.Shape();
		this.shape_27.graphics.f("#FFFFFF").s().p("AgVA2QgLgEgGgJQgGgKAAgLIAOgBQABAIAEAGQAEAFAHAEQAIADAIAAQAJAAAGgCQAHgDADgEQAEgFAAgFQAAgGgEgEQgDgEgHgDIgSgFQgQgEgGgBQgJgFgEgGQgEgGAAgIQAAgJAFgHQAFgIAKgEQAJgEALAAQALAAAKAEQAKAEAFAIQAFAIABAKIgPACQgBgLgHgGQgHgFgMAAQgMAAgHAFQgGAFAAAHQAAAGAEAEQAFAEAQAEQATAEAHADQAKADAEAHQAFAHAAAJQAAAKgFAIQgFAIgKAEQgKAFgMAAQgOAAgKgFg");
		this.shape_27.setTransform(-52.7,-26.4);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_27},{t:this.shape_26},{t:this.shape_25},{t:this.shape_24},{t:this.shape_23},{t:this.shape_22},{t:this.shape_21},{t:this.shape_20},{t:this.shape_19},{t:this.shape_18},{t:this.shape_17},{t:this.shape_16},{t:this.shape_15},{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-60,-37.2,142,61.7);


	(lib.t2 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("#FFFFFF").s().p("AgEArIAAgbIgWAIIAAgDQAagLATgRQgKgQgFgSQgKAVgLALIgCgBQASgaAGgiIANAIIgGADIgGANIAlAAIAFgGIAKAJIgGADQgMAUgKANQARAMAZAEIAAACQgIACgCAFQgXgKgPgKQgNAKgNAHIAEADIAoAAIAFgFIAJAIIgFADIAAAYIABARIgJAEIAAgKIgpAAIAAAJIgGADIAAgcgAACA3IApAAIAAgiIgpAAgAAYgNQAKgLAJgTIgmAAQAIATALALgAhHAvIARgDIAkgIIABACQgQAFgOAGQgOAGgDAEgAhDAOQAGgCAIgKQAIgHAHgOIgPADQgGABgFAEIgFgMQAFAAAJgQQAJgQAFgPIAMAIIgHAEIgZAkIAZAAIAKgTIAKAJIgFADQgSAagRARIAlgGIAAACQgRAEgJAEQgJADgFAFg");
		this.shape.setTransform(58,50.4);

		this.shape_1 = new cjs.Shape();
		this.shape_1.graphics.f("#FFFFFF").s().p("AAbA4IAAgDIASAAQAEAAgBgIIAAhmIhkAAIAAB4IgJAFIAAhJIAAg+IAJAHIBjAAIAFgHIAKAHIgFAFIAABrQABALgOAFQgBgJgQgDgAgGAjQANgTAIgVQgKgQgIgNIACgBIATAXQAHgQACgQIAMAIQgDACgEAJIgIATQALAMADAGQADAGgBAEQAAAEgDAEQgEADgDgLQgDgLgHgLQgMAXgNAOgAgwAgQALgSAJgXQgIgNgKgNIACgCQALALAIAKQAFgNADgPIAMAIIgFAEIgKAWIALAQQADAFgBAFQgBAFgDADQgDADgCgKQgCgLgFgHQgKASgNAQg");
		this.shape_1.setTransform(42.2,50.6);

		this.shape_2 = new cjs.Shape();
		this.shape_2.graphics.f("#FFFFFF").s().p("AAOBGQgQgBgMgFQgMgEgHgHQgHgJgDABQgDAAgHAIQgHAHgDAGIgJgJQAKgIAPgIIAAgzIgIAAQgFAAgHACIgFgGIAYAAIAFgGIAKAHIgGAFIAAAwQAFAGAKAHQALAGAZABQAbABAigEIAAADQgMADAAAIIgvgBgAAOgEQgRAegfARIgBgCQAggaALgcIgVAAIgMACIgGgGIAtAAIgBg1IAQAHIgHAEIAAAqIAbAAIAJgIIAMAMIgwAAIAAAIQATAIALAJQAMAJAAAFQAAAGgDAEQgCAEgEgHQgEgIgIgJQgHgJgOgIIAAA2IgJAGIABg/gAAbgUQAOgUAHgSIANALIgHACQgMAPgNALgAgJggQgCgKgJgMIACgBQAIAEAFAGQAFAEAAAFQAAAEgCAGQgBAAAAAAQAAABgBAAQAAAAAAAAQgBABAAAAQgDAAgBgIgAgxgkQgBgJgIgOIACgBQAJAFAEAFQAFAEABAEQAAAFgFAFIgDACQgDAAgBgGg");
		this.shape_2.setTransform(26,50.4);

		this.shape_3 = new cjs.Shape();
		this.shape_3.graphics.f("#FFFFFF").s().p("AgZAJQgIAKgMAIQgMAJgNAGIgBgCQASgNAMgLQAMgLAJgLIgcAAQgIAAgHACIgGgGIAzAAIAIgPIgSAAQgJAAgGACIgHgGIApAAIAFgPIglAAQgIAAgHACIgGgFIAvAAQgCgNgJgKIABgBQAPAHADAFQAEAFgHAHIAZAAQAJgTACgGIAMAHQgGABgOARIAdAAIAJgJIAMAMIhBAAIgDAPIAfAAIAJgIIALAMIg0AAIgIAPIA9AAIAJgIIALAMIhTAAIgIAIIADADIA1AAIAEgFIAKAHIgGAEIABA6IgJADIAAgJIg1AAIAAAGIgJAEIAAhAgAgQA7IA1AAIAAgOIg1AAgAgQApIA1AAIAAgOIg1AAgAgQAXIA1AAIAAgOIg1AAg");
		this.shape_3.setTransform(9.9,50.6);

		this.shape_4 = new cjs.Shape();
		this.shape_4.graphics.f("#FFFFFF").s().p("AgoAZIgBg1IAKAEIA3AAIAGgGIAJAJIgFAEIABAZIgKAEIAAgHIg4AAIAAAVIA8AAIAGgHIAJAJIgFAEIABAiIgKAEIAAgIIg9AAIAAAIIgKACIABgvgAgfA5IA9AAIAAgcIg9AAgAgfABIA4AAIAAgVIg4AAgAhCgYQgEgCAGgGQAGgFAEgPIADAAIAAAHIBlAAIAHgHIALANQgKAAgMAPIgDgBIAIgQIhmAAQAAANgFAEQgCACgDAAIgFgCgAgDg0QgDgJgJgJIACgBQAMAFADAEQAGAFgFAIQgCADgBAAQgBAAgCgGg");
		this.shape_4.setTransform(132.8,30.6);

		this.shape_5 = new cjs.Shape();
		this.shape_5.graphics.f("#FFFFFF").s().p("AgpgCQgOANgOAJIgBgCQANgMAMgPQAMgQAHgPIAKAJIgGAEIgNASIAGAGIgDADIAABDIgJAFIAAhKgAgDA5IAAgDIAVABQAGABgBgIIAAg+IgXAAQgGAAgIACIgFgGIBGAAIAJgJIALANIgnAAIAABBQABARgQADQAAgJgUgFgAhBgbQALgKAIgNQAKgMADgJIALAJQgFABgKANQgLALgQAMgAgIg3IAzAAIAIgIIALALIgzAAQgJAAgFACg");
		this.shape_5.setTransform(116.7,30.7);

		this.shape_6 = new cjs.Shape();
		this.shape_6.graphics.f("#FFFFFF").s().p("AghBHQAbgWAIgjQgIgIgHgDIAAgDIAQAHQADgJABgXIgNABIgFADIgHgGIAZgCIgCgqIAQAHIgGAEIAAAfIAVgDIAEgGIAKAHIgFAEQgDAUACAYQACAbAOAKIAJgcIADAAQgCAIgBAKQgBAMADAHQAEAIgLgEQgKgEgIgMQgIgNgCgRQgCgSADgeIgWABQAAAWgDAPQAOAHADAFQADAEgCAGQgCAGgFgHQgFgHgIgHQgLAkgfATgAhBA7IAAgDQAPACAFAAQAEgBAAgGIAAgtIgUAKIgDAEIgHgLQAGgBAYgIIAAgfIgKAAQgIAAgHACIgFgGIAeAAIAAglIAPAHIgGADIAAAbIAIAAIAHgHIALALIgaAAIAAAdIAWgKIACADIgYAKIAAAzQABAOgPAGQABgIgUgFg");
		this.shape_6.setTransform(100.6,30.7);

		this.shape_7 = new cjs.Shape();
		this.shape_7.graphics.f("#FFFFFF").s().p("AAGAUIgbAAIAAAmIgJAEIABgaIgBgYIAKAEIAaAAIAAgQIgKAAIAAADIgJADIABgbIgSAAIgFAAIgGgEIAdAAIgBgVIANAGIgEAEIAAALIAdAAIgBgVIAOAGIgFAEIAAALIAPAAIAIgIIAMAMIgjAAIABAYIgJACIAAgFIgLAAIAAAQIAgAAIAFgGIAJAIIgFADIAAAYQABAMgNAEQgBgHgPgEIAAgCIAPAAQAGAAgBgHIAAgVIghAAIABAxIgJAEIAAg1gAgEgDIAdAAIAAgSIgdAAgAhGBFQAPgfAAglIABg6IAJAGIAyAAQgBgLgHgIIABgCQAMAGACAEQADAEgFAHIApAAIAJgJIALAMIh0AAQAAAogDAYQgCAbgSAbg");
		this.shape_7.setTransform(84.5,30.7);

		this.shape_8 = new cjs.Shape();
		this.shape_8.graphics.f("#FFFFFF").s().p("AgoAbIgBgwIAKAFIASAAQADgLABgKIgsAAQgEAAgIADIgFgGIAzAAIgDgNQgDgGgHgIIABgCQANAHAGAGQAGAGgIAKIAXAAQAJgPAEgRIAOAJIgGADIgQAUIAjAAIAJgJIANAMIhFAAQgFAIgHANIApAAIADgHIALAIIgEAEIAAA7IAAATIgJAFIAAgOIg/AAIAAAKIgKAFQABgJAAglgAgfA3IA/AAIAAgWIg/AAgAgfAeIA/AAIAAgUIg/AAgAgfAGIA/AAIAAgSIg/AAg");
		this.shape_8.setTransform(68.7,30.7);

		this.shape_9 = new cjs.Shape();
		this.shape_9.graphics.f("#FFFFFF").s().p("AAFAHQgFgLgSgLIABgCQAOADAGAFQAIAEADAEQAEACABAGQABAGgFAEIgCABQgEAAgEgLg");
		this.shape_9.setTransform(53.7,34.8);

		this.shape_10 = new cjs.Shape();
		this.shape_10.graphics.f("#FFFFFF").s().p("AhHBFQAagQANgTQAOgTAFgbQAEgdAAgdIAQAIIgGAGQAEAhAJAXQAJAVAPAQQAQAQASADIAAADQgMABgFAIQgRgMgLgQQgLgQgHgSQgHgQgCggQgBAegGASQgHAVgPAQQgPARgaALg");
		this.shape_10.setTransform(42,30.6);

		this.shape_11 = new cjs.Shape();
		this.shape_11.graphics.f("#FFFFFF").s().p("AhFBHQAWgSALgTQgQgJgKgEIAOgxIgBAAQgIAAgHACIgGgHIAXAAQAGgaABgMIAPAIIgGADIgIAbIAUAAIAEgFIAKAIIgFAEQgHAkgIAUQALAHAEAEQADAEgCAIQgBAHgGgIQgFgIgIgHQgPAXgXAMgAg2AUIAUAHQAKgVAFgiIgUAAIgPAwgAADAmIgBgjIAJAGIAoAAIAFgHIAKAIIgFAFIABAyIgJAFIAAgNIgqAAIAAALIgJADIABghgAALA2IAqAAIAAgqIgqAAgAgFgQQAFgDAHgOQAIgOAHgYIAOAIIgGADQgTAjgIAJIA3gCQgHgMgKgLIACgCQAZARACAHQADAHgFAFQgEAGgCgFIgDgJQgbACgOADQgNADgFAEg");
		this.shape_11.setTransform(25.8,30.6);

		this.shape_12 = new cjs.Shape();
		this.shape_12.graphics.f("#FFFFFF").s().p("AAgA7IAAgDQASACAEgBQADAAAAgHIAAhgIAAgWIANAHIgEAFIAABvQAAALgOAGQgEgJgQgEgAgkBBQgMAAAAgNIAAhFQgKAMgKAHIgBgCQAQgTAKgUQAJgTADgNIAOAIIgGAFQAXAKAGAHQAGAGgDAGQgCAGgFgHQgDgIgIgGQgHgGgIgFQgPAagIAJIAHAFIAeAAIAGgFIAGAJIgDADQAAAagDAGQgDAGgLAFQgDgJgKgDIAAgDQAMADADgBQADgBACgFIACgbIgfAAIAAA6QgBAKAIAAIAhAAQACAAACgDQABgEACgXIACAAIACASQABAHAFADQgEAIgGABIgHABgAAagGIgBgjIAOAHIgFAFIAAAnIABARIgKADIABgkg");
		this.shape_12.setTransform(9.7,30.6);

		this.shape_13 = new cjs.Shape();
		this.shape_13.graphics.f("#FFFFFF").s().p("Ag8gCQAAg3gBgMIAJAHIAbAAIAFgGIALAJQgHADgGAMIgNAYQAKAIAGAHQAGAGAAAJQAAAJgFAHQgFAHgLADQgCgIgLgEIAAgDQAQAEAFgGQAFgFgBgJQgBgHgQgSIAOgmIgbAAIAAB7IgJAFQABgSAAg2gAgDAAIgBg+IAHAGIAtAAIAEgGIAKAIIgFAEIAABSIAAAbIgIAEIAAgRIguAAIAAAOIgHAEIABhAgAADAqIAuAAIAAgtIguAAgAADgHIAuAAIAAgtIguAAg");
		this.shape_13.setTransform(26,11);

		this.shape_14 = new cjs.Shape();
		this.shape_14.graphics.f("#FFFFFF").s().p("AhBAAIgBhDIAJAHIAXAAIAGgHIAJAIIgFADIgRAnQALAKAEAHQAFAIgBAGQAAAHgDAGQgEAFgLAGQgDgKgMgEIABgCQAQAEAEgEQAEgDgBgNQgCgJgMgOIAMgoIgYAAIAAB9IgJAFIABhJgAgDA5IAAgCIATACQADgBAAgDIAAg0IgbAAIgFAFIgIgGQAFgCAGgMIAKgdIgDAAQgIAAgHACIgFgGIAZAAQAGgRABgIIANAHQgFADgHAPIAlAAIAIgIIAKAMIg4AAQgJAYgIARIAbAAIgBgdIANAGIgEADIAAAUIAWAAIAHgHIALAKIgoAAIAAA2QAAAGgDADQgCADgIAEQgFgKgMgEgAgkA6QAMgKAKgOQAKgOADgKIALAJIgGADQgXAfgRAIgAA3ArQgGgOgOgLIABgDQAVAOAGAHQAGAIgEAGQAAABAAAAQgBABAAAAQAAAAgBABQAAAAAAAAQgDAAgFgKg");
		this.shape_14.setTransform(10.2,10.9);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,0,142.9,61.7);


	(lib.t1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("#FFFFFF").s().p("AgbAgQgKgLAAgVQAAgWAMgLQALgJAOAAQARAAAKAMQALAKAAAUQAAAOgEAKQgFAIgJAGQgKAFgKAAQgQAAgLgLgAgQgXQgHAJAAAOQAAAPAHAJQAHAHAJABQAKgBAHgHQAHgJAAgPQAAgPgHgHQgHgIgKAAQgJAAgHAHg");
		this.shape.setTransform(-21.6,15);

		this.shape_1 = new cjs.Shape();
		this.shape_1.graphics.f("#FFFFFF").s().p("AgGA5IAAhRIANAAIAABRgAgGgoIAAgQIANAAIAAAQg");
		this.shape_1.setTransform(-27.8,13.4);

		this.shape_2 = new cjs.Shape();
		this.shape_2.graphics.f("#FFFFFF").s().p("AgFApIgghRIAQAAIARAvIAEASIAFgRIASgwIAOAAIgfBRg");
		this.shape_2.setTransform(-33.5,15);

		this.shape_3 = new cjs.Shape();
		this.shape_3.graphics.f("#FFFFFF").s().p("AgbAgQgKgLAAgVQAAgWAMgLQALgJAOAAQARAAAKAMQALAKAAAUQAAAOgEAKQgFAIgJAGQgKAFgKAAQgQAAgLgLgAgQgXQgHAJAAAOQAAAPAHAJQAHAHAJABQAKgBAHgHQAHgJAAgPQAAgPgHgHQgHgIgKAAQgJAAgHAHg");
		this.shape_3.setTransform(-42,15);

		this.shape_4 = new cjs.Shape();
		this.shape_4.graphics.f("#FFFFFF").s().p("AAfA5IgPgYIgLgQIgGgIIgGgDIgJAAIgSAAIAAAzIgPAAIAAhxIAxAAQAPAAAIADQAIADAFAIQAEAIAAAJQAAANgHAIQgIAGgQADIAJAFQAGAGAGAKIAUAfgAgigFIAhAAQAIAAAGgCQAGgDADgEQADgFAAgGQAAgIgGgFQgGgFgMAAIgjAAg");
		this.shape_4.setTransform(-51.7,13.4);

		this.shape_5 = new cjs.Shape();
		this.shape_5.graphics.f("#FFFFFF").s().p("AgOBFQAYgMARgTQgHgNgCgPIgDgbIhCAAQgIAAgHACIgFgGIApAAIAAgUIgMAAQgIAAgHACIgGgFIAhAAIgBgbIAPAIIgGAEIAAAPIANAAIAHgHIAJAKIgdAAIAAAUIAlAAIgBgyIAPAHIgFAEIAAAnIAcAAIAIgJIAMANIgwAAQABAfAHAQQANgTAHgQIAMAHIgGACQgMAVgKANQALANANAHIAIgcIACABQgCAQAAAJQABAJABAEQACAEgGgBQgGgCgKgHQgKgHgJgMQgTATgZAJgAgtA6QACgEAAgDIAAgWQgKAMgQAJIgCgCQAZgTALgYIgNAAQgIAAgHACIgGgFIBBAAIAGgGIAKAJIgmAAIgIAPIAAAiIAXgKIABACQgXAQgFAFgAgFAtQgIgLgRgNIABgBIAPAIQAKgKAEgJIAIAJIgIADIgMAIQAKAGADAEQAFAFgDAGQAAABAAAAQgBABAAAAQAAAAgBAAQAAABAAAAQgBAAgFgIgAggAAQgCgFgIgKIACgBIANAIQAEADgDAFQgBAAAAAAQgBAAAAAAQgBABAAAAQAAAAgBAAQAAAAAAAAQgBAAAAgBQAAAAAAAAQgBAAAAAAgAAtgnQgDgJgIgKIABgBQAOAJAEAEQAEAFgEAGQgBAAAAAAQAAABgBAAQAAAAAAAAQgBABAAAAQgDAAgCgGg");
		this.shape_5.setTransform(62,-6.6);

		this.shape_6 = new cjs.Shape();
		this.shape_6.graphics.f("#FFFFFF").s().p("AgQBFQgOAAABgNIAAghIAOAIIgFAEIAAATQAAAHAGAAIAlAAQAGAAABgGQACgHAAgNIADAAIABASQABAFAGACQgEAJgKAAgAhBA6QgDgCAIgHQAIgGAFgPIACAAQgBAUgDAFQgDAGgFABIgCAAQgDAAgDgCgAA5AzQgFgKgOgNIABgCQAMAFAHAFQAIAEABAFQABAFgDAFQgBABAAAAQAAABgBAAQAAAAAAAAQgBAAAAAAQgDAAgCgGgAADAmQgDgJgLgNIACgBQANAHAFAFQAGAFgEAHQgBABAAABQgBAAAAABQgBAAAAAAQgBAAAAAAQgCAAgCgEgAgngHIAAgcIAJAFIAlAAQALgUAFgSIANAKIgGABIgTAbIAZAAIAEgGIAKAIIgFAEQAAAdABAKIgKAEIAAgHIhCAAIAAAFIgJADIAAgbgAgeAIIBCAAIAAgiIhCAAgAgSgpQgBgJgJgNIACgCQAKAHAFAFQAFAFgBAFQgCAFgDADIgCABQgDAAgBgHg");
		this.shape_6.setTransform(45.8,-6.9);

		this.shape_7 = new cjs.Shape();
		this.shape_7.graphics.f("#FFFFFF").s().p("AAgA6IAAgCIAUABQABAAAAAAQABgBAAAAQAAgBAAAAQAAgBAAgBIAAhjIAAgZIAOAHIgFAEIAABzQAAAHgCADQgDAEgGADQgEgJgQgFgAg+AiIAAgiIAJACIA2AAIAGgDIAIAGIgFADIAAApIABANIgJAFIAAgKIg4AAIAAAKIgIADIAAgkgAgVA1IAXAAIAAgXIgXAAgAg2A1IAaAAIAAgXIgaAAgAgVAaIAXAAIAAgUIgXAAgAg2AaIAaAAIAAgUIgaAAgAAZgJIgBgoIAOAIIgFADIAAAwIABASIgKAFIABgqgAgzgaIgBgVIAJAFIAlAAIAGgGIAGAIIgFAEIAAALIAAAOIgGADIAAgHIgmAAIAAAHIgJADIABgVgAgrgSIAmAAIAAgUIgmAAgAhDg7IBDAAIAGgHIALALIg/AAQgLAAgFACg");
		this.shape_7.setTransform(29.8,-6.4);

		this.shape_8 = new cjs.Shape();
		this.shape_8.graphics.f("#FFFFFF").s().p("AgpgCQgOANgOAJIgBgBQANgOAMgOQANgQAFgPIALAJIgFAEIgOASIAHAFIgEAEIAABCIgKAGIABhKgAgCA5IAAgDIAUABQAFAAABgHIAAg+IgYAAQgGAAgIACIgFgFIBGAAIAKgJIAKAMIgnAAIAABBQABARgRADQAAgJgSgFgAhBgaQAKgLAKgNQAJgMACgJIAMAJQgEABgLAMQgKAMgRANgAgIg3IAzAAIAJgIIAKAMIg0AAQgIAAgEABg");
		this.shape_8.setTransform(14.1,-6.5);

		this.shape_9 = new cjs.Shape();
		this.shape_9.graphics.f("#FFFFFF").s().p("AghBHQAbgVAIglQgIgGgHgEIAAgCIAQAGQADgKABgWIgNABIgFACIgHgFIAZgCIgCgqIAQAHIgGAEIAAAeIAVgCIAEgHIAKAIIgFAFQgDATACAYQACAbAOAKIAJgcIADABQgCAGgBAMQgBALADAIQAEAHgLgEQgKgEgIgNQgIgLgCgSQgCgSADgeIgWABQAAAWgDAPQAOAHADAEQADAFgCAGQgCAGgFgHQgFgHgIgHQgLAkgfATgAhBA6IAAgCQAPACAFgBQAEAAAAgHIAAgsIgUALIgDADIgHgLQAGgBAYgIIAAgfIgKAAQgIAAgHACIgFgFIAeAAIAAgmIAPAHIgGAEIAAAbIAIAAIAHgIIALALIgaAAIAAAcIAWgJIACADIgYAKIAAAzQABAPgPAFQABgHgUgHg");
		this.shape_9.setTransform(-2.1,-6.5);

		this.shape_10 = new cjs.Shape();
		this.shape_10.graphics.f("#FFFFFF").s().p("AAUghIAAgdIAJAHIAcAAIAFgGIAJAJIgGAFIgSAoQALAIAFAKQAFAJAAAJQAAAIgFAGQgFAGgJACQgDgIgOgEIAAgDIARADQAGAAACgGQADgFgDgMQgCgLgMgMIAOgtIgcAAIAAB4IgJAEgAg1AkIAAgeIAJAGIAkAAIAGgHIAGAJIgEAEIAAAqIgIAFIAAgPIgkAAIAAANIgJAFIAAgggAgsAvIAkAAIAAggIgkAAgAhGgHIAyAAQAKgWABgKIALAIIgDADIgPAVIASAAIAHgHIAKAMIhIAAQgFAAgGABgAgpgNIgDgLQgCgFgFgGIACgCQALAIADAEQADAFgBADQgBAEgDADIgCABQAAAAgBAAQAAAAAAgBQgBAAAAgBQAAgBAAgBgAhAgvIAlAAIgDgMQgCgFgEgGIABgCQAQAKABAGQABAFgGAEIAUAAIAFgHIAKALIg6AAQgFABgHACg");
		this.shape_10.setTransform(-18,-6.4);

		this.shape_11 = new cjs.Shape();
		this.shape_11.graphics.f("#FFFFFF").s().p("AA5BEQgRgIgPgXQgZAUgYALIgBgCQAagQATgVQgGgSgFgbIgPABIgFADIgHgFIAagDIgFgzIARAGIgFAGQAAATADASIAjgDIAIgJIAMAKIg3AHQAEAXAFAPQATgVAFgNIALALQgFACgGAHQgHAJgNAOQANATAQAIIAHgfIADAAQgCASAAALQAAAKADAGQABAAAAAAQAAABAAAAQAAAAgBAAQAAAAAAAAQgDAAgLgEgAhIBBQAXgcALgbQgNgOgMgNIAAgCQARAMALAKQAIgRADgWIgXAAQgIAAgIACIgFgGIArAAIAGgIIALAJIgGAFQgHAdgHAPIANAPQAFAGAAAGQAAAFgEAEQgCADgDgJQgDgIgLgOQgRAggUAQgAAsgnQgDgHgOgNIABgBQAQAFAFADQAGAEgBAEQABAFgDAEIgDABQgCAAgDgFg");
		this.shape_11.setTransform(-34.1,-6.5);

		this.shape_12 = new cjs.Shape();
		this.shape_12.graphics.f("#FFFFFF").s().p("AgzBHQAQgTAIgWQAHgXAAgmIAAAAIAAAAIgGACIgFgHIAdAAIAEgGIAJALIgWAAIgCAWIANAAIAFgGIAIAIIgFAEQgDA2gFAJQgDAHgIACQAAgIgNgGIABgCQAKAEAGgBQAFgBACgXIABgmIgOAAQgCAegKASQgJARgQANgAASA8IAAgCIAOABQAFAAAAgFIAAglIgGAAQgIAAgHACIgGgFIAbAAIgBgUIAEADIANgQIgQAAQgIAAgHACIgFgGIAkAAIAHgGIAJAKQgHABgHAGIgMAKIAGACIgEABIAAANIAKAAIAIgIIAJALIgbAAIAAAmQAAAOgMACQAAgHgPgEgAg4A8QgHgDADgMQAEgMgDgDQgEgDgIgDIAAgCIAMABQADgBAEgGQADgEAQgzIACAAIgQBAQgCAHAAAMIAAAQQAAAAgBABQAAAAAAAAQAAABgBAAQAAAAgBAAIgEgCgAg8gLQgCgJgJgMIABgCQAUALAAAGQAAAHgEADIgCABQgCAAgCgFgAAHgTQAKgQAFgOQAFgOABgJIANAIIgGADIgKATIAcAAIAJgHIAJALIgwAAQgHALgIAJgAgvguQgDgIgJgMIABgBQARAIACAEQADAFgEAGQAAABgBAAQAAAAAAABQgBAAAAAAQAAAAgBAAQgCAAgCgEgAgNguQgCgJgJgLIABgCQANAIAEAFQAFAFgFAFQgBABAAABQgBAAAAAAQgBABAAAAQAAAAgBAAQgCAAgBgEg");
		this.shape_12.setTransform(-50.1,-6.5);

		this.shape_13 = new cjs.Shape();
		this.shape_13.graphics.f("#FFFFFF").s().p("AgBA1QgEgCgCgDQgCgFAAgMIAAguIgKAAIAAgLIAKAAIAAgVIAMgIIAAAdIAPAAIAAALIgPAAIAAAvQAAAFABACQAAAAABABQAAAAAAABQAAAAABAAQAAABABAAIAEABIAHgBIACANIgLABQgIAAgCgDg");
		this.shape_13.setTransform(33.2,-26.2);

		this.shape_14 = new cjs.Shape();
		this.shape_14.graphics.f("#FFFFFF").s().p("AATA5IAAg1QAAgIgEgFQgFgFgIAAQgEAAgGADQgFAEgDAFQgCAEAAAJIAAAuIgOAAIAAhxIAOAAIAAApQAKgLANAAQAJAAAHAEQAHADACAHQADAGAAAKIAAA1g");
		this.shape_14.setTransform(26.4,-26.4);

		this.shape_15 = new cjs.Shape();
		this.shape_15.graphics.f("#FFFFFF").s().p("AgeAkQgHgHAAgKQABgGACgFQADgFAEgDQAFgBAFgCIANgCQAPgCAIgCIAAgFQAAgIgEgDQgGgFgJAAQgJAAgEAEQgGADgBAIIgPgBQADgJAEgGQAEgFAJgDQAHgDAJAAQALAAAHACQAGADADAEQADAEABAFIABAOIAAAQQAAAUABAFIAEAKIgPAAIgDgKQgHAGgIADQgFADgJAAQgNAAgIgHgAgCAEIgMAEQgEABgCADQgCADABADQAAAGADAEQAFAEAIAAQAGAAAGgEQAHgDADgHQACgEAAgKIAAgEQgHACgOACg");
		this.shape_15.setTransform(17.5,-24.8);

		this.shape_16 = new cjs.Shape();
		this.shape_16.graphics.f("#FFFFFF").s().p("AgGA5IAAhjIgmAAIAAgOIBZAAIAAAOIgmAAIAABjg");
		this.shape_16.setTransform(8.3,-26.4);

		this.shape_17 = new cjs.Shape();
		this.shape_17.graphics.f("#FFFFFF").s().p("AAqAqIAAgyQAAgIgCgFQgBgDgEgCQgDgCgFgBQgJAAgGAGQgFAGAAANIAAAuIgMAAIAAg0QAAgJgEgFQgDgFgIAAQgGABgFADQgFACgCAHQgDAFAAALIAAAqIgOAAIAAhRIANAAIAAAMQAEgHAGgDQAHgEAIAAQAJAAAGAEQAEAEACAHQAKgPAQAAQAMAAAHAHQAHAHAAAOIAAA3g");
		this.shape_17.setTransform(-7.8,-24.9);

		this.shape_18 = new cjs.Shape();
		this.shape_18.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape_18.setTransform(-16.3,-26.4);

		this.shape_19 = new cjs.Shape();
		this.shape_19.graphics.f("#FFFFFF").s().p("AgaAfQgLgKAAgVQAAgSALgNQALgLAPAAQARAAAKALQALAMAAATIAAADIg8AAQAAAOAHAHQAHAIAJgBQAHAAAGgEQAFgEAEgIIAOABQgDANgKAHQgJAHgOAAQgRAAgKgMgAgOgYQgHAGgBALIAtAAQgBgKgFgFQgGgJgLAAQgIAAgGAHg");
		this.shape_19.setTransform(-22.5,-24.8);

		this.shape_20 = new cjs.Shape();
		this.shape_20.graphics.f("#FFFFFF").s().p("AATA5IAAg1QAAgIgEgFQgFgFgIAAQgEAAgGADQgFAEgDAFQgCAEAAAJIAAAuIgOAAIAAhxIAOAAIAAApQAKgLANAAQAJAAAHAEQAHADACAHQADAGAAAKIAAA1g");
		this.shape_20.setTransform(-31.4,-26.4);

		this.shape_21 = new cjs.Shape();
		this.shape_21.graphics.f("#FFFFFF").s().p("AgGA5IAAhxIANAAIAABxg");
		this.shape_21.setTransform(-37.6,-26.4);

		this.shape_22 = new cjs.Shape();
		this.shape_22.graphics.f("#FFFFFF").s().p("AgGA5IAAhRIANAAIAABRgAgGgoIAAgQIANAAIAAAQg");
		this.shape_22.setTransform(-41.1,-26.4);

		this.shape_23 = new cjs.Shape();
		this.shape_23.graphics.f("#FFFFFF").s().p("AAbA5IgYhWIgDgNIgCANIgYBWIgPAAIgfhxIAPAAIASBJIAFAYIAFgVIAVhMIARAAIAQA4QAHAWACATIAGgZIAShIIAPAAIgfBxg");
		this.shape_23.setTransform(-50.4,-26.4);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_23},{t:this.shape_22},{t:this.shape_21},{t:this.shape_20},{t:this.shape_19},{t:this.shape_18},{t:this.shape_17},{t:this.shape_16},{t:this.shape_15},{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-60,-37.2,142,61.7);


	(lib.xxx = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// bg
		this.instance = new lib.bgtest();

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,0,750,1205);


	(lib.补间38 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.dc();
		this.instance.setTransform(-28,-23);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-28,-23,56,46);


	(lib.p2_img1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 2
		this.instance = new lib.p2();
		this.instance.setTransform(-257,-182);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

		// Layer 1
		this.instance_1 = new lib.u9();
		this.instance_1.setTransform(-155,-134);

		this.timeline.addTween(cjs.Tween.get(this.instance_1).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-257,-182,447,456);


	(lib.补间34 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p4_3();
		this.instance.setTransform(-242.5,-89.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-242.5,-89.5,485,179);


	(lib.补间33 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p4_3();
		this.instance.setTransform(-242.5,-89.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-242.5,-89.5,485,179);


	(lib.补间32 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p4_2();
		this.instance.setTransform(-242.5,-36);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-242.5,-36,485,72);


	(lib.补间31 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p4_2();
		this.instance.setTransform(-242.5,-36);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-242.5,-36,485,72);


	(lib.补间30 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p4_1();
		this.instance.setTransform(-344,-259.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-344,-259.5,688,519);


	(lib.补间29 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p4_1();
		this.instance.setTransform(-344,-259.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-344,-259.5,688,519);


	(lib.补间28 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_9();
		this.instance.setTransform(-135.5,-24);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-135.5,-24,271,48);


	(lib.补间27 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_9();
		this.instance.setTransform(-135.5,-24);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-135.5,-24,271,48);


	(lib.补间26 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_8();
		this.instance.setTransform(-106.5,-24);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-106.5,-24,213,48);


	(lib.补间25 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_8();
		this.instance.setTransform(-106.5,-24);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-106.5,-24,213,48);


	(lib.补间24 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_7();
		this.instance.setTransform(-102.5,-24);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-102.5,-24,205,48);


	(lib.补间23 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_7();
		this.instance.setTransform(-102.5,-24);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-102.5,-24,205,48);


	(lib.补间22 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_6();
		this.instance.setTransform(-109,-24);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-109,-24,218,48);


	(lib.补间21 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_6();
		this.instance.setTransform(-109,-24);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-109,-24,218,48);


	(lib.补间17 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_1();
		this.instance.setTransform(-169.5,-58);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-169.5,-58,339,116);


	(lib.补间16 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1_3();
		this.instance.setTransform(-315,-100.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-315,-100.5,630,201);


	(lib.补间15 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1_3();
		this.instance.setTransform(-315,-100.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-315,-100.5,630,201);


	(lib.补间13 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1_2();
		this.instance.setTransform(-271.5,-105);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-271.5,-105,543,210);


	(lib.补间12 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1_2();
		this.instance.setTransform(-271.5,-105);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-271.5,-105,543,210);


	(lib.补间11 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1_1();
		this.instance.setTransform(-216,-132.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-216,-132.5,432,265);


	(lib.补间10 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1_1();
		this.instance.setTransform(-216,-132.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-216,-132.5,432,265);


	(lib.补间8 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1();
		this.instance.setTransform(-305,-310.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-305,-310.5,610,621);


	(lib.补间7 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1();
		this.instance.setTransform(-305,-310.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-305,-310.5,610,621);


	(lib.补间5 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p1();
		this.instance.setTransform(-305,-310.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-305,-310.5,610,621);


	(lib.元件4 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_5();

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,0,74,108);


	(lib.元件2 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.p3_3();

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,0,750,524);


	(lib.元件11 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("#02BFAA").s().p("AmDGEQihihAAjjQgBhzArhiQALgbAOgaQAlhAA5g5QBKhLBZgoQAcgMAegKQBPgZBXABQDjAAChChQChChAADiQAADjihChQihChjjAAQjiAAihihg");

		this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-55,-55,110,110);


	(lib.元件10 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.shape = new cjs.Shape();
		this.shape.graphics.f("rgba(255,255,255,0.008)").s().p("AxZFrIAArVMAizAAAIAALVg");
		this.shape.setTransform(111.4,36.4);

		this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,0,222.8,72.7);


	(lib.元件9 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.de();
		this.instance.setTransform(-56.5,-56.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-56.5,-56.5,113,113);


	(lib.元件8 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.u2();
		this.instance.setTransform(-49,-49);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-49,-49,98,98);


	(lib.元件7 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.u3();
		this.instance.setTransform(-49,-49);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-49,-49,98,98);


	(lib.元件6 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.u4();
		this.instance.setTransform(-49,-49);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-49,-49,98,98);


	(lib.元件5 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.u6();
		this.instance.setTransform(-49,-49);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-49,-49,98,98);


	(lib.元件4_1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance_1 = new lib.u5();
		this.instance_1.setTransform(-49,-49);

		this.timeline.addTween(cjs.Tween.get(this.instance_1).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-49,-49,98,98);


	(lib.元件3 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.u7();
		this.instance.setTransform(-49,-49);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-49,-49,98,98);


	(lib.元件1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.de();
		this.instance.setTransform(-56.5,-56.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-56.5,-56.5,113,113);


	(lib.dddd8 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			/* Mouse Click 事件
			 单击此指定的元件实例会执行您可在其中添加自己的自定义代码的函数。

			 说明:
			 1. 在以下"// 开始您的自定义代码"行后的新行上添加您的自定义代码。
			 单击此元件实例时，此代码将执行。
			 */

			this.movieClip_1.addEventListener("click", fl_MouseClickHandler.bind(this));

			function fl_MouseClickHandler()
			{


				showTipHuman(8);

			}
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 1
		this.instance = new lib.u8();

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

		// 图层 2
		this.movieClip_1 = new lib.元件1();
		this.movieClip_1.setTransform(49,49);

		this.timeline.addTween(cjs.Tween.get(this.movieClip_1).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-7.5,-7.5,113,113);


	(lib.dddd7 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.movieClip_3.addEventListener("click", fl_MouseClickHandler_3.bind(this));

			function fl_MouseClickHandler_3()
			{
				showTipHuman(7);
			}
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 1
		this.movieClip_3 = new lib.元件3();
		this.movieClip_3.setTransform(49,49);

		this.timeline.addTween(cjs.Tween.get(this.movieClip_3).wait(1));

		// 图层 2
		this.instance = new lib.de();
		this.instance.setTransform(-7.5,-7.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-7.5,-7.5,113,113);


	(lib.dddd6 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.movieClip_5.addEventListener("click", fl_MouseClickHandler_5.bind(this));

			function fl_MouseClickHandler_5()
			{
				showTipHuman(6);
			}
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 1
		this.movieClip_5 = new lib.元件5();
		this.movieClip_5.setTransform(49,49);

		this.timeline.addTween(cjs.Tween.get(this.movieClip_5).wait(1));

		// 图层 2
		this.instance = new lib.de();
		this.instance.setTransform(-7.5,-7.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-7.5,-7.5,113,113);


	(lib.dddd5 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.movieClip_4.addEventListener("click", fl_MouseClickHandler_4.bind(this));

			function fl_MouseClickHandler_4()
			{
				showTipHuman(5);
			}
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 1
		this.movieClip_4 = new lib.元件4_1();
		this.movieClip_4.setTransform(49,49);

		this.timeline.addTween(cjs.Tween.get(this.movieClip_4).wait(1));

		// 图层 2
		this.instance = new lib.de();
		this.instance.setTransform(-7.5,-7.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-7.5,-7.5,113,113);


	(lib.dddd4 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.movieClip_6.addEventListener("click", fl_MouseClickHandler_6.bind(this));

			function fl_MouseClickHandler_6()
			{
				showTipHuman(4);
			}
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 1
		this.movieClip_6 = new lib.元件6();
		this.movieClip_6.setTransform(49,49);

		this.timeline.addTween(cjs.Tween.get(this.movieClip_6).wait(1));

		// 图层 2
		this.instance = new lib.de();
		this.instance.setTransform(-7.5,-7.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-7.5,-7.5,113,113);


	(lib.dddd3 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			/* Mouse Click 事件
			 单击此指定的元件实例会执行您可在其中添加自己的自定义代码的函数。

			 说明:
			 1. 在以下"// 开始您的自定义代码"行后的新行上添加您的自定义代码。
			 单击此元件实例时，此代码将执行。
			 */

			this.movieClip_7.addEventListener("click", fl_MouseClickHandler_7.bind(this));

			function fl_MouseClickHandler_7()
			{
				showTipHuman(3);
			}
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 1
		this.movieClip_7 = new lib.元件7();
		this.movieClip_7.setTransform(49,49);

		this.timeline.addTween(cjs.Tween.get(this.movieClip_7).wait(1));

		// 图层 2
		this.instance = new lib.de();
		this.instance.setTransform(-7.5,-7.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-7.5,-7.5,113,113);


	(lib.dddd2 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.movieClip_8.addEventListener("click", fl_MouseClickHandler_8.bind(this));

			function fl_MouseClickHandler_8()
			{
				showTipHuman(2);
			}
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 1
		this.movieClip_8 = new lib.元件8();
		this.movieClip_8.setTransform(49,49);

		this.timeline.addTween(cjs.Tween.get(this.movieClip_8).wait(1));

		// 图层 2
		this.instance = new lib.de();
		this.instance.setTransform(-7.5,-7.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-7.5,-7.5,113,113);


	(lib.dddd = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.movieClip_9.addEventListener("click", fl_MouseClickHandler_9.bind(this));

			function fl_MouseClickHandler_9()
			{
				showTipHuman(1);
			}
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 1
		this.instance = new lib.u1();

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

		// 图层 2
		this.movieClip_9 = new lib.元件9();
		this.movieClip_9.setTransform(49,49);

		this.timeline.addTween(cjs.Tween.get(this.movieClip_9).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-7.5,-7.5,113,113);


	(lib.p3_1_1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.补间17("synched",0);
		this.instance.setTransform(169.5,58);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,0,339,116);


	(lib.补间6 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 1
		this.instance = new lib.补间7("synched",0);

		this.instance_1 = new lib.补间8("synched",0);
		this.instance_1.setTransform(0,0,1,1,180);
		this.instance_1._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance).to({_off:true,rotation:180},120).wait(120));
		this.timeline.addTween(cjs.Tween.get(this.instance_1).to({_off:false},120).to({rotation:270},60).to({rotation:360},59).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-305,-310.5,610,621);


	(lib.元件5_1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_9 = function() {
			this.stop();
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).wait(9).call(this.frame_9).wait(1));

		// 图层 1
		this.instance_1 = new lib.元件4();
		this.instance_1.setTransform(37,-6,1,1,0,0,0,37,52);
		this.instance_1.alpha = 0.461;
		new cjs.ButtonHelper(this.instance_1, 0, 1, 1);

		this.timeline.addTween(cjs.Tween.get(this.instance_1).to({y:52,alpha:1},9).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,-58,74,108);


	(lib.anim4 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.stop();
		}
		this.frame_86 = function() {
			this.button_10.addEventListener("click", fl_MouseClickHandler_10.bind(this));

			function fl_MouseClickHandler_10()
			{
				showSign();
			}


			this.button_11.addEventListener("click", fl_MouseClickHandler_11.bind(this));

			function fl_MouseClickHandler_11()
			{
				showShare();
			}
		}
		this.frame_119 = function() {
			this.stop();
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(86).call(this.frame_86).wait(33).call(this.frame_119).wait(1));

		// 图层 5
		this.button_11 = new lib.元件10();
		this.button_11.setTransform(493,693,1,1,0,0,0,111.4,36.4);
		new cjs.ButtonHelper(this.button_11, 0, 1, 1);

		this.button_10 = new lib.元件10();
		this.button_10.setTransform(228.7,693,1,1,0,0,0,111.4,36.4);
		new cjs.ButtonHelper(this.button_10, 0, 1, 1);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.button_10},{t:this.button_11}]},86).wait(34));

		// 图层 4
		this.instance = new lib.补间33("synched",0);
		this.instance.setTransform(350.9,1373.1);
		this.instance._off = true;

		this.instance_1 = new lib.补间34("synched",0);
		this.instance_1.setTransform(360.7,877.4);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance}]},42).to({state:[{t:this.instance_1}]},53).wait(25));
		this.timeline.addTween(cjs.Tween.get(this.instance).wait(42).to({_off:false},0).to({_off:true,x:360.7,y:877.4},53).wait(25));

		// 图层 3
		this.instance_2 = new lib.补间31("synched",0);
		this.instance_2.setTransform(360.7,1629.8);
		this.instance_2._off = true;

		this.instance_3 = new lib.补间32("synched",0);
		this.instance_3.setTransform(360.7,627.3);
		this.instance_3._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_2).wait(20).to({_off:false},0).to({_off:true,y:627.3},52).wait(48));
		this.timeline.addTween(cjs.Tween.get(this.instance_3).wait(20).to({_off:false},52).to({y:693.3},14).wait(34));

		// 图层 2
		this.instance_4 = new lib.补间29("synched",0);
		this.instance_4.setTransform(379.1,1470.9);

		this.instance_5 = new lib.补间30("synched",0);
		this.instance_5.setTransform(379.1,276.2);
		this.instance_5._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_4).to({_off:true,y:276.2},60,cjs.Ease.get(-1)).wait(60));
		this.timeline.addTween(cjs.Tween.get(this.instance_5).to({_off:false},60,cjs.Ease.get(-1)).to({y:339.8},24,cjs.Ease.get(1)).wait(36));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,0,723.1,1730.4);


	(lib.anim3 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.stop();
		}
		this.frame_163 = function() {
			this.movieClip_11.addEventListener("click", fl_MouseClickHandler_11.bind(this));

			function fl_MouseClickHandler_11()
			{
				showMap(2);
			}


			this.movieClip_12.addEventListener("click", fl_MouseClickHandler_12.bind(this));

			function fl_MouseClickHandler_12()
			{
				showMap(4);
			}



			this.movieClip_13.addEventListener("click", fl_MouseClickHandler_13.bind(this));

			function fl_MouseClickHandler_13()
			{
				showMap(1);
			}


			this.movieClip_14.addEventListener("click", fl_MouseClickHandler_14.bind(this));

			function fl_MouseClickHandler_14()
			{
				showMap(3);
			}
		}
		this.frame_202 = function() {
			this.stop();
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(163).call(this.frame_163).wait(39).call(this.frame_202).wait(1));

		// 图层 2
		this.instance = new lib.p3_1_1();
		this.instance.setTransform(374.7,196,1,1,0,0,0,169.5,58);
		this.instance.alpha = 0;

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(4).to({alpha:1},0).wait(5).to({alpha:0},0).wait(5).to({alpha:1},0).wait(5).to({alpha:0},0).wait(5).to({alpha:1},0).wait(179));

		// 图层 17
		this.instance_1 = new lib.补间27("synched",0);
		this.instance_1.setTransform(520.5,641);
		this.instance_1.alpha = 0.43;
		this.instance_1._off = true;

		this.instance_2 = new lib.补间28("synched",0);
		this.instance_2.setTransform(520.5,629.5);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_1}]},152).to({state:[{t:this.instance_2}]},10).wait(41));
		this.timeline.addTween(cjs.Tween.get(this.instance_1).wait(152).to({_off:false},0).to({_off:true,y:629.5,alpha:1},10).wait(41));

		// 图层 16
		this.instance_3 = new lib.补间25("synched",0);
		this.instance_3.setTransform(137.5,613.2);
		this.instance_3.alpha = 0.43;
		this.instance_3._off = true;

		this.instance_4 = new lib.补间26("synched",0);
		this.instance_4.setTransform(137.5,599.2);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_3}]},143).to({state:[{t:this.instance_4}]},9).wait(51));
		this.timeline.addTween(cjs.Tween.get(this.instance_3).wait(143).to({_off:false},0).to({_off:true,y:599.2,alpha:1},9).wait(51));

		// 图层 15
		this.instance_5 = new lib.补间23("synched",0);
		this.instance_5.setTransform(551.5,448.5);
		this.instance_5.alpha = 0.43;
		this.instance_5._off = true;

		this.instance_6 = new lib.补间24("synched",0);
		this.instance_6.setTransform(551.5,428.5);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_5}]},134).to({state:[{t:this.instance_6}]},9).wait(60));
		this.timeline.addTween(cjs.Tween.get(this.instance_5).wait(134).to({_off:false},0).to({_off:true,y:428.5,alpha:1},9).wait(60));

		// 图层 13
		this.instance_7 = new lib.补间21("synched",0);
		this.instance_7.setTransform(208,374.4);
		this.instance_7.alpha = 0.43;
		this.instance_7._off = true;

		this.instance_8 = new lib.补间22("synched",0);
		this.instance_8.setTransform(208,348.4);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_7}]},124).to({state:[{t:this.instance_8}]},10).wait(69));
		this.timeline.addTween(cjs.Tween.get(this.instance_7).wait(124).to({_off:false},0).to({_off:true,y:348.4,alpha:1},10).wait(69));

		// 图层 12
		this.movieClip_13 = new lib.元件5_1();
		this.movieClip_13.setTransform(520,691.1,1,1,0,0,0,37,-4);
		this.movieClip_13._off = true;

		this.timeline.addTween(cjs.Tween.get(this.movieClip_13).wait(115).to({_off:false},0).wait(88));

		// 图层 11
		this.movieClip_14 = new lib.元件5_1();
		this.movieClip_14.setTransform(140.2,658.2,1,1,0,0,0,37,-4);
		this.movieClip_14._off = true;

		this.timeline.addTween(cjs.Tween.get(this.movieClip_14).wait(104).to({_off:false},0).wait(99));

		// 图层 10
		this.movieClip_12 = new lib.元件5_1();
		this.movieClip_12.setTransform(548.9,488.6,1,1,0,0,0,37,-4);
		this.movieClip_12._off = true;

		this.timeline.addTween(cjs.Tween.get(this.movieClip_12).wait(97).to({_off:false},0).wait(106));

		// 图层 9
		this.movieClip_11 = new lib.元件5_1();
		this.movieClip_11.setTransform(214.2,466.5,1,1,0,0,0,37,52);
		this.movieClip_11._off = true;

		this.timeline.addTween(cjs.Tween.get(this.movieClip_11).wait(87).to({_off:false},0).wait(116));

		// 图层 7
		this.instance_9 = new lib.元件2();
		this.instance_9.setTransform(375,565.4,1.631,1.631,0,0,0,375,262);
		this.instance_9.alpha = 0;
		this.instance_9.compositeOperation = "lighter";
		this.instance_9._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_9).wait(67).to({_off:false},0).to({scaleX:1,scaleY:1,alpha:1},19,cjs.Ease.get(1)).wait(117));

		// 图层 4 (mask)
		var mask = new cjs.Shape();
		mask._off = true;
		var mask_graphics_26 = new cjs.Graphics().p("Eg6iA/AMAAAhIBMB1FAAAMAAABIBg");
		var mask_graphics_27 = new cjs.Graphics().p("Eg6hA/BMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_28 = new cjs.Graphics().p("Eg6hA/BMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_29 = new cjs.Graphics().p("Eg6hA/BMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_30 = new cjs.Graphics().p("Eg6iA/BMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_31 = new cjs.Graphics().p("Eg6iA/BMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_32 = new cjs.Graphics().p("Eg6iA/BMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_33 = new cjs.Graphics().p("Eg6hA/BMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_34 = new cjs.Graphics().p("Eg6hA/BMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_35 = new cjs.Graphics().p("Eg6hA/BMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_36 = new cjs.Graphics().p("Eg6iA/BMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_37 = new cjs.Graphics().p("Eg6hA/BMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_38 = new cjs.Graphics().p("Eg6iA/BMAAAhIBMB1FAAAMAAABIBg");
		var mask_graphics_39 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_40 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_41 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_42 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_43 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_44 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_45 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_46 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_47 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_48 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_49 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_50 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_51 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_52 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_53 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_54 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_55 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_56 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_57 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_58 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_59 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1FAAAMAAABICg");
		var mask_graphics_60 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_61 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_62 = new cjs.Graphics().p("Eg6iA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_63 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_64 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1EAAAMAAABICg");
		var mask_graphics_65 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_66 = new cjs.Graphics().p("Eg6hA/CMAAAhICMB1DAAAMAAABICg");
		var mask_graphics_67 = new cjs.Graphics().p("Eg6iA/DMAAAhIDMB1FAAAMAAABIDg");

		this.timeline.addTween(cjs.Tween.get(mask).to({graphics:null,x:0,y:0}).wait(26).to({graphics:mask_graphics_26,x:-374.7,y:403.3}).wait(1).to({graphics:mask_graphics_27,x:-338.6,y:403.3}).wait(1).to({graphics:mask_graphics_28,x:-303.4,y:403.3}).wait(1).to({graphics:mask_graphics_29,x:-269,y:403.3}).wait(1).to({graphics:mask_graphics_30,x:-235.6,y:403.3}).wait(1).to({graphics:mask_graphics_31,x:-203.1,y:403.3}).wait(1).to({graphics:mask_graphics_32,x:-171.4,y:403.4}).wait(1).to({graphics:mask_graphics_33,x:-140.7,y:403.4}).wait(1).to({graphics:mask_graphics_34,x:-110.8,y:403.4}).wait(1).to({graphics:mask_graphics_35,x:-81.8,y:403.4}).wait(1).to({graphics:mask_graphics_36,x:-53.7,y:403.4}).wait(1).to({graphics:mask_graphics_37,x:-26.5,y:403.4}).wait(1).to({graphics:mask_graphics_38,x:-0.2,y:403.4}).wait(1).to({graphics:mask_graphics_39,x:25.2,y:403.4}).wait(1).to({graphics:mask_graphics_40,x:49.7,y:403.4}).wait(1).to({graphics:mask_graphics_41,x:73.3,y:403.4}).wait(1).to({graphics:mask_graphics_42,x:96.1,y:403.4}).wait(1).to({graphics:mask_graphics_43,x:117.9,y:403.4}).wait(1).to({graphics:mask_graphics_44,x:138.9,y:403.4}).wait(1).to({graphics:mask_graphics_45,x:158.9,y:403.4}).wait(1).to({graphics:mask_graphics_46,x:178.1,y:403.4}).wait(1).to({graphics:mask_graphics_47,x:196.4,y:403.5}).wait(1).to({graphics:mask_graphics_48,x:213.8,y:403.5}).wait(1).to({graphics:mask_graphics_49,x:230.3,y:403.5}).wait(1).to({graphics:mask_graphics_50,x:245.9,y:403.5}).wait(1).to({graphics:mask_graphics_51,x:260.6,y:403.5}).wait(1).to({graphics:mask_graphics_52,x:274.4,y:403.5}).wait(1).to({graphics:mask_graphics_53,x:287.3,y:403.5}).wait(1).to({graphics:mask_graphics_54,x:299.4,y:403.5}).wait(1).to({graphics:mask_graphics_55,x:310.5,y:403.5}).wait(1).to({graphics:mask_graphics_56,x:320.8,y:403.5}).wait(1).to({graphics:mask_graphics_57,x:330.1,y:403.5}).wait(1).to({graphics:mask_graphics_58,x:338.6,y:403.5}).wait(1).to({graphics:mask_graphics_59,x:346.2,y:403.5}).wait(1).to({graphics:mask_graphics_60,x:352.9,y:403.5}).wait(1).to({graphics:mask_graphics_61,x:358.7,y:403.5}).wait(1).to({graphics:mask_graphics_62,x:363.6,y:403.5}).wait(1).to({graphics:mask_graphics_63,x:367.6,y:403.5}).wait(1).to({graphics:mask_graphics_64,x:370.7,y:403.5}).wait(1).to({graphics:mask_graphics_65,x:372.9,y:403.5}).wait(1).to({graphics:mask_graphics_66,x:374.3,y:403.5}).wait(1).to({graphics:mask_graphics_67,x:374.7,y:403.5}).wait(136));

		// 图层 3
		this.instance_10 = new lib.p3_2();
		this.instance_10.setTransform(0,372.4);
		this.instance_10._off = true;

		this.instance_10.mask = mask;

		this.timeline.addTween(cjs.Tween.get(this.instance_10).wait(26).to({_off:false},0).wait(177));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(205.2,138,339,116);


	(lib.arrowdo = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.gotoAndPlay(73);
		}
		this.frame_70 = function() {
			this.gotoAndPlay(22);
		}
		this.frame_146 = function() {
			this.gotoAndPlay(97);
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(70).call(this.frame_70).wait(76).call(this.frame_146).wait(1));

		// 图层 2
		this.instance = new lib.补间38("synched",0);
		this.instance.setTransform(55.5,44.5);
		this.instance._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(71).to({_off:false},0).to({rotation:180,y:74.5},25).to({y:44.5},25,cjs.Ease.get(1)).to({y:74.5},25).wait(1));

		// 图层 1
		this.movieClip_15 = new lib.元件11();
		this.movieClip_15.setTransform(55,55);
		this.movieClip_15._off = true;

		this.timeline.addTween(cjs.Tween.get(this.movieClip_15).wait(71).to({_off:false},0).wait(76));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = null;


	(lib.wwwww = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_991 = function() {
			this.gotoAndPlay(81);
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).wait(991).call(this.frame_991).wait(83));

		// 图层 8
		this.instance = new lib.dddd8();
		this.instance.setTransform(360.1,532.3,0.28,0.28,0,0,0,49,49);
		this.instance._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(24).to({_off:false},0).to({scaleX:1,scaleY:1,x:217,y:451},12).wait(44).to({scaleX:1.42,scaleY:1.42,x:388.6,y:296.1},12).wait(98).to({scaleX:1,scaleY:1,x:542,y:451},15).wait(100).to({x:587,y:612},15).wait(100).to({x:540,y:756},15).wait(100).to({x:387,y:825},15).wait(100).to({x:217,y:756},15).wait(100).to({x:156,y:612},15).wait(99).to({x:217,y:451},16).wait(98).to({x:542},0).wait(80).to({x:217.6,y:450.9},0).wait(1));

		// 图层 7
		this.instance_1 = new lib.dddd7();
		this.instance_1.setTransform(377.3,529.2,0.335,0.335,0,0,0,49.1,49.1);
		this.instance_1._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_1).wait(13).to({_off:false},0).to({regX:49,regY:49,scaleX:1,scaleY:1,x:156,y:612},12).wait(55).to({x:217,y:451},12).wait(98).to({scaleX:1.42,scaleY:1.42,x:385.4,y:296.1},15).wait(100).to({scaleX:1,scaleY:1,x:542,y:451},15).wait(100).to({x:587,y:612},15).wait(100).to({x:540,y:756},15).wait(100).to({x:387,y:825},15).wait(100).to({x:217,y:756},15).wait(99).to({x:156,y:612},16).wait(98).to({x:217,y:451},0).wait(80).to({x:156.6,y:612.1},0).wait(1));

		// 图层 6
		this.instance_2 = new lib.dddd6();
		this.instance_2.setTransform(364.8,532.3,0.362,0.362,0,0,0,49.1,49.1);
		this.instance_2._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_2).wait(15).to({_off:false},0).to({regX:49,regY:49,scaleX:1,scaleY:1,x:217,y:756},11).wait(54).to({x:156,y:612},12).wait(98).to({x:217,y:451},15).wait(100).to({scaleX:1.42,scaleY:1.42,x:385.4,y:288.1},15).wait(100).to({scaleX:1,scaleY:1,x:542,y:451},15).wait(100).to({x:587,y:612},15).wait(100).to({x:540,y:756},15).wait(100).to({x:387,y:825},15).wait(99).to({x:217,y:756},16).wait(98).to({x:156,y:612},0).wait(80).to({x:217.6,y:756.2},0).wait(1));

		// 图层 5
		this.instance_3 = new lib.dddd5();
		this.instance_3.setTransform(388.8,591.8,0.501,0.501,0,0,0,49,49);
		this.instance_3._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_3).wait(30).to({_off:false},0).to({scaleX:1,scaleY:1,x:387,y:825.1},11).wait(39).to({x:217,y:756},12).wait(98).to({x:156,y:612},15).wait(100).to({x:217,y:451},15).wait(100).to({scaleX:1.42,scaleY:1.42,x:384.6,y:278.5},15).wait(100).to({scaleX:1,scaleY:1,x:542,y:451},15).wait(100).to({x:587,y:612},15).wait(100).to({x:540,y:756},15).wait(99).to({x:387,y:825},16).wait(98).to({x:217,y:756},0).wait(80).to({x:388.8,y:825.1},0).wait(1));

		// 图层 4
		this.instance_4 = new lib.dddd4();
		this.instance_4.setTransform(403.9,558.9,0.39,0.39,0,0,0,49,49);
		this.instance_4._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_4).wait(22).to({_off:false},0).to({scaleX:1,scaleY:1,x:540,y:756},11).wait(47).to({x:387,y:825},12).wait(98).to({x:217,y:756},15).wait(100).to({x:156,y:612},15).wait(100).to({x:217,y:451},15).wait(100).to({scaleX:1.42,scaleY:1.42,x:386.2,y:281.7},15).wait(100).to({scaleX:1,scaleY:1,x:542,y:451},15).wait(100).to({x:587,y:612},15).wait(99).to({x:540,y:756},16).wait(98).to({x:387,y:825},0).wait(80).to({x:540.1,y:756.2},0).wait(1));

		// 图层 3
		this.instance_5 = new lib.dddd3();
		this.instance_5.setTransform(385.3,552.8,0.39,0.39,0,0,0,49,49);
		this.instance_5._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_5).wait(15).to({_off:false},0).to({scaleX:1,scaleY:1,x:587,y:612},12).wait(53).to({x:540,y:756},12).wait(98).to({x:387,y:825},15).wait(100).to({x:217,y:756},15).wait(100).to({x:156,y:612},15).wait(100).to({x:217,y:451},15).wait(100).to({scaleX:1.42,scaleY:1.42,x:384.6,y:281.7},15).wait(100).to({scaleX:1,scaleY:1,x:542,y:451},15).wait(99).to({x:587,y:612},16).wait(98).to({x:540,y:756},0).wait(80).to({x:587.1,y:612.1},0).wait(1));

		// 图层 2
		this.instance_6 = new lib.dddd2();
		this.instance_6.setTransform(367.9,522.9,0.446,0.446,0,0,0,49,49);
		this.instance_6._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance_6).wait(9).to({_off:false},0).to({scaleX:1,scaleY:1,x:542,y:451},11).wait(60).to({x:587,y:612},12).wait(98).to({x:540,y:756},15).wait(100).to({x:387,y:825},15).wait(100).to({x:217,y:756},15).wait(100).to({x:156,y:612},15).wait(100).to({x:217,y:451},15).wait(100).to({scaleX:1.42,scaleY:1.42,x:388.6,y:290.5},15).wait(99).to({scaleX:1,scaleY:1,x:542,y:451},16).wait(178).to({x:541.7,y:450.9},0).wait(1));

		// 图层 1
		this.instance_7 = new lib.dddd();
		this.instance_7.setTransform(388.8,526.2,0.696,0.696,0,0,0,49,49);
		this.instance_7.shadow = new cjs.Shadow("rgba(255,0,0,1)",0,0,0);

		this.timeline.addTween(cjs.Tween.get(this.instance_7).to({scaleX:1.42,scaleY:1.42,x:389.9,y:308.8},14).wait(66).to({scaleX:1,scaleY:1,x:542,y:451},12).wait(98).to({x:587,y:612},15).wait(100).to({x:540,y:756},15).wait(100).to({x:387,y:825},15).wait(100).to({x:217,y:756},15).wait(100).to({x:156,y:612},15).wait(100).to({x:217,y:451},15).wait(99).to({scaleX:1.42,scaleY:1.42,x:381.4,y:288.1},16).wait(98).to({scaleX:1,scaleY:1,x:389,y:311},0).wait(80).to({x:388.8,y:311.7},0).wait(1));

		// 图层 13
		this.instance_8 = new lib.t7();
		this.instance_8.setTransform(664.5,273.5,1,1,0,0,0,11,3.6);

		this.instance_9 = new lib.t6();
		this.instance_9.setTransform(700.4,476.6,1,1,0,0,0,30.1,-6.4);

		this.instance_10 = new lib.t5();
		this.instance_10.setTransform(414.5,940.2,1,1,0,0,0,11,-6.4);

		this.instance_11 = new lib.t4();
		this.instance_11.setTransform(633,931.2,1,1,0,0,0,42.5,2.6);

		this.instance_12 = new lib.t3();
		this.instance_12.setTransform(100.3,901.8,1,1,0,0,0,11,-6.4);

		this.instance_13 = new lib.t2();
		this.instance_13.setTransform(83.8,476.7,1,1,0,0,0,71.4,30.9);

		this.instance_14 = new lib.t1();
		this.instance_14.setTransform(100.3,324.7,1,1,0,0,0,11,-6.4);

		this.instance_15 = new lib.Glu();
		this.instance_15.setTransform(208,86.6);

		this.instance_16 = new lib.Rovio();
		this.instance_16.setTransform(212.5,69.1);

		this.instance_17 = new lib.t8();
		this.instance_17.setTransform(657,285.3,1,1,0,0,0,11,-6.4);

		this.instance_18 = new lib.t5();
		this.instance_18.setTransform(100.3,904.9,1,1,0,0,0,11,-6.4);

		this.instance_19 = new lib.t4();
		this.instance_19.setTransform(428,947.5,1,1,0,0,0,42.5,2.6);

		this.instance_20 = new lib.zmwl();
		this.instance_20.setTransform(212.5,49.3);

		this.instance_21 = new lib.Blizzard();
		this.instance_21.setTransform(212.5,54);

		this.instance_22 = new lib.EA();
		this.instance_22.setTransform(212.5,61.8);

		this.instance_23 = new lib.NCsoft();
		this.instance_23.setTransform(212.5,61.8);

		this.instance_24 = new lib.Colopl();
		this.instance_24.setTransform(212.5,39.9);

		this.instance_25 = new lib.BANDAINAMCOGAMES();
		this.instance_25.setTransform(219.5,35.2);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_15,p:{x:208,y:86.6}},{t:this.instance_14,p:{x:100.3,y:324.7}},{t:this.instance_13,p:{regX:71.4,regY:30.9,x:83.8,y:476.7}},{t:this.instance_12,p:{x:100.3,y:901.8}},{t:this.instance_11,p:{x:633,y:931.2}},{t:this.instance_10,p:{x:414.5,y:940.2}},{t:this.instance_9,p:{regX:30.1,regY:-6.4,x:700.4,y:476.6}},{t:this.instance_8,p:{regY:3.6,x:664.5,y:273.5}}]},60).to({state:[]},20).to({state:[{t:this.instance_13,p:{regX:11,regY:-6.4,x:40.3,y:287.4}},{t:this.instance_19},{t:this.instance_18},{t:this.instance_9,p:{regX:11,regY:3.6,x:628.9,y:914.9}},{t:this.instance_8,p:{regY:-6.4,x:675.9,y:456.7}},{t:this.instance_12,p:{x:83.4,y:476.6}},{t:this.instance_11,p:{x:137.9,y:773.1}},{t:this.instance_10,p:{x:-189.9,y:730.5}},{t:this.instance_17,p:{x:657,y:285.3}},{t:this.instance_16}]},37).to({state:[]},73).to({state:[{t:this.instance_14,p:{x:657,y:279.7}},{t:this.instance_11,p:{x:131.8,y:913.9}},{t:this.instance_10,p:{x:83.4,y:476.6}},{t:this.instance_9,p:{regX:11,regY:3.6,x:398.7,y:940}},{t:this.instance_8,p:{regY:-6.4,x:640.5,y:903}},{t:this.instance_12,p:{x:100.3,y:319.8}},{t:this.instance_17,p:{x:694.2,y:476.6}},{t:this.instance_20}]},30).to({state:[]},85).to({state:[{t:this.instance_14,p:{x:682.6,y:476.6}},{t:this.instance_13,p:{regX:11,regY:-6.4,x:596.1,y:248.1}},{t:this.instance_11,p:{x:110,y:481.4}},{t:this.instance_10,p:{x:100.3,y:322.9}},{t:this.instance_9,p:{regX:11,regY:3.6,x:94.5,y:913}},{t:this.instance_8,p:{regY:-6.4,x:403.5,y:932.8}},{t:this.instance_17,p:{x:640.5,y:903}},{t:this.instance_21}]},31).to({state:[]},84).to({state:[{t:this.instance_14,p:{x:640.5,y:912.3}},{t:this.instance_13,p:{regX:11,regY:-6.4,x:616.6,y:427}},{t:this.instance_11,p:{x:131.8,y:315.1}},{t:this.instance_9,p:{regX:11,regY:3.6,x:70.5,y:474.2}},{t:this.instance_8,p:{regY:-6.4,x:100.3,y:904.6}},{t:this.instance_12,p:{x:657,y:279}},{t:this.instance_17,p:{x:412.5,y:933.3}},{t:this.instance_22}]},37).to({state:[]},78).to({state:[{t:this.instance_14,p:{x:401.5,y:937.4}},{t:this.instance_13,p:{regX:11,regY:-6.4,x:580.5,y:859.9}},{t:this.instance_10,p:{x:657,y:285.3}},{t:this.instance_9,p:{regX:11,regY:3.6,x:81.3,y:331.4}},{t:this.instance_8,p:{regY:-6.4,x:83.4,y:450.5}},{t:this.instance_12,p:{x:689.9,y:470.4}},{t:this.instance_17,p:{x:96,y:906.1}},{t:this.instance_23}]},39).to({state:[]},76).to({state:[{t:this.instance_14,p:{x:100.3,y:906}},{t:this.instance_13,p:{regX:11,regY:-6.4,x:341.5,y:905.4}},{t:this.instance_11,p:{x:682.1,y:283.7}},{t:this.instance_10,p:{x:689.9,y:464.2}},{t:this.instance_8,p:{regY:-6.4,x:100.3,y:298.3}},{t:this.instance_12,p:{x:640.5,y:906}},{t:this.instance_17,p:{x:88,y:465.7}},{t:this.instance_24}]},43).to({state:[]},72).to({state:[{t:this.instance_14,p:{x:83.4,y:464.2}},{t:this.instance_13,p:{regX:11,regY:-6.4,x:40.3,y:868.8}},{t:this.instance_11,p:{x:714.1,y:473.2}},{t:this.instance_10,p:{x:640.5,y:912.3}},{t:this.instance_9,p:{regX:11,regY:3.6,x:637.9,y:295.3}},{t:this.instance_12,p:{x:401.5,y:945.3}},{t:this.instance_17,p:{x:101.2,y:315.8}},{t:this.instance_25}]},41).to({state:[]},73).to({state:[{t:this.instance_14,p:{x:100.3,y:318.3}},{t:this.instance_13,p:{regX:11,regY:-6.4,x:23.4,y:427}},{t:this.instance_11,p:{x:672,y:921.3}},{t:this.instance_10,p:{x:422.5,y:937.4}},{t:this.instance_9,p:{regX:11,regY:3.6,x:684.5,y:474.2}},{t:this.instance_8,p:{regY:-6.4,x:647.8,y:262.3}},{t:this.instance_12,p:{x:96,y:912.3}},{t:this.instance_15,p:{x:212.5,y:58.7}}]},37).wait(158));

		// 图层 14
		this.shape = new cjs.Shape();
		this.shape.graphics.f().s("#FFFFFF").ss(1,1,1).p("EAeSgg1IDInrIVoAAA/79IIkIlRIyDAAEglUgGaIiwlTIwrAAEAk3gGqIDTlBIQmAAEAbxAirICXFAITlAAEgd2Ai5IkKFoI0xAA");
		this.shape.setTransform(375.7,596.3);
		this.shape._off = true;

		this.timeline.addTween(cjs.Tween.get(this.shape).wait(60).to({_off:false},0).wait(1014));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(345.5,482.9,95,95);


	(lib.page4 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// ani
		this.ani = new lib.anim4();

		this.timeline.addTween(cjs.Tween.get(this.ani).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(35.1,1211.4,688,519);


	(lib.page3 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// ani
		this.ani = new lib.anim3();
		this.ani.setTransform(320,531,1,1,0,0,0,320,531);

		this.timeline.addTween(cjs.Tween.get(this.ani).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(205.2,138,339,116);


	(lib.c1_1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// 图层 4
		this.instance = new lib.补间6("synched",0);
		this.instance.setTransform(381,410.5);

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(239).to({startPosition:0},0).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(76,100,610,621);


	(lib.page5 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// ani
		this.ani = new lib.anim4();
		this.ani.setTransform(320,531,1,1,0,0,0,320,531);

		this.timeline.addTween(cjs.Tween.get(this.ani).wait(1));

		// bg
		this.text = new cjs.Text("PAGE 4", "40px 'Arial'", "#FFFFFF");
		this.text.textAlign = "center";
		this.text.lineHeight = 47;
		this.text.lineWidth = 358;
		this.text.setTransform(318,0);

		this.shape = new cjs.Shape();
		this.shape.graphics.f("#9999FF").s().p("Egx/BS+MAAAil7MBj+AAAMAAACl7g");
		this.shape.setTransform(320,531);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape},{t:this.text}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(0,0,723.1,1730.4);


	(lib.anim2 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			this.stop();
		}
		this.frame_129 = function() {
			this.stop();
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(129).call(this.frame_129).wait(1));

		// 图层 4
		this.instance = new lib.wwwww();
		this.instance.setTransform(371.8,568.4,1,1,0,0,0,371.8,568.4);
		this.instance._off = true;

		this.timeline.addTween(cjs.Tween.get(this.instance).wait(40).to({_off:false},0).wait(90));

		// p2_img1
		this.instance_1 = new lib.p2_img1();
		this.instance_1.setTransform(-161.4,560);
		this.instance_1.alpha = 0;
		this.instance_1.cache(-259,-184,451,460);

		this.timeline.addTween(cjs.Tween.get(this.instance_1).to({rotation:360,x:421.1,alpha:1},39,cjs.Ease.get(1)).wait(91));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-418.4,0,1058.5,1062);


	(lib.anim1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_149 = function() {
			this.stop();
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).wait(149).call(this.frame_149).wait(1));

		// 图层 5
		this.instance = new lib.补间5("synched",0);
		this.instance.setTransform(381,410.5,3.511,3.511);

		this.instance_1 = new lib.补间6("synched",0);
		this.instance_1.setTransform(381,410.5);

		this.instance_2 = new lib.c1_1();
		this.instance_2.setTransform(380.5,410.4,1,1,0,0,0,381,410.5);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.instance}]}).to({state:[{t:this.instance_1}]},28).to({state:[{t:this.instance_2}]},1).wait(121));
		this.timeline.addTween(cjs.Tween.get(this.instance).to({_off:true,scaleX:1,scaleY:1},28).wait(122));

		// 图层 9
		this.instance_3 = new lib.补间15("synched",0);
		this.instance_3.setTransform(-315,907.3);
		this.instance_3._off = true;

		this.instance_4 = new lib.补间16("synched",0);
		this.instance_4.setTransform(370.5,907.3);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_3}]},107).to({state:[{t:this.instance_4}]},42).wait(1));
		this.timeline.addTween(cjs.Tween.get(this.instance_3).wait(107).to({_off:false},0).to({_off:true,x:370.5},42,cjs.Ease.get(0.5)).wait(1));

		// 图层 8
		this.instance_5 = new lib.补间12("synched",0);
		this.instance_5.setTransform(378.3,576.4,0.224,0.224);
		this.instance_5._off = true;

		this.instance_6 = new lib.补间13("synched",0);
		this.instance_6.setTransform(378.3,576.4);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_5}]},68).to({state:[{t:this.instance_6}]},47).wait(35));
		this.timeline.addTween(cjs.Tween.get(this.instance_5).wait(68).to({_off:false},0).to({_off:true,scaleX:1,scaleY:1},47,cjs.Ease.get(0.5)).wait(35));

		// 图层 7
		this.instance_7 = new lib.补间10("synched",0);
		this.instance_7.setTransform(419.1,343.8,0.348,0.348);
		this.instance_7._off = true;

		this.instance_8 = new lib.补间11("synched",0);
		this.instance_8.setTransform(419.1,343.8);

		this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_7}]},29).to({state:[{t:this.instance_8}]},46).wait(75));
		this.timeline.addTween(cjs.Tween.get(this.instance_7).wait(29).to({_off:false},0).to({_off:true,scaleX:1,scaleY:1},46,cjs.Ease.get(0.5)).wait(75));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-689.9,-679.7,2141.9,2180.5);


	(lib.page2 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// ani
		this.ani = new lib.anim2();
		this.ani.setTransform(320,531,1,1,0,0,0,320,531);

		this.timeline.addTween(cjs.Tween.get(this.ani).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-418.4,378,447,456);


	(lib.page1 = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// ani
		this.ani = new lib.anim1();

		this.timeline.addTween(cjs.Tween.get(this.ani).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(-689.9,-679.7,2141.9,2180.5);


// stage content:
	(lib.index = function(mode,startPosition,loop) {
		this.initialize(mode,startPosition,loop,{});

		// timeline functions:
		this.frame_0 = function() {
			root = this;

			setTimeout(function(){

				var screenWidth = window.screen.width;
				var screenHeight = window.screen.height;
				var stageWidth = 750;
				var stageHeight = 1205;
				var animScaleRatio = (screenHeight*2-128)/stageHeight;

				var scene = document.body;
				var container;
				var duration = .5;
				var pages = {};
				var pageTotal = 4;
				var pageID = 1;

				var oldX, oldY, newX, newY, deltaX, deltaY;

				container = new createjs.MovieClip();
				root.addChild(container);

				for (var i = 1; i <= pageTotal; i++) {
					eval('pages["p"+i] = new lib.page'+i+'()');
					container.addChild(pages["p"+i]);
					pages["p"+i].y = (i-1)*stageHeight;

					if(animScaleRatio<1){
						//pages["p"+i].ani.scaleX = animScaleRatio;
						//pages["p"+i].ani.scaleY = animScaleRatio;
						//pages["p"+i].ani.y = stageHeight*animScaleRatio*.5;
					}
				}

				playAnimate();
				//container.cache(0, 0, stageWidth, stageHeight*pageTotal, 1);

				scene.addEventListener("touchstart", function bodyTouchStart(e){
					if(scene.scrollTop!=0){
						//window.scrollTo(0,0);
						TweenMax.to(window, .5, { scrollTo:{y:0}, ease:Cubic.easeInOut, overwrite:"auto" });
					}
					oldY = e.changedTouches[0].pageY;
				});

				scene.addEventListener("touchmove", function bodyTouchMove(e){
					e.preventDefault();
				});

				scene.addEventListener("touchend", function bodyTouchEnd(e){

					newY = e.changedTouches[0].pageY;
					deltaY = newY - oldY;

					//swipe up
					if(deltaY<0){
						if(pageID<pageTotal){


							pageID++;
							TweenMax.to(container, duration, { y:-(pageID-1)*stageHeight, ease:Cubic.easeOut, overwrite:1, onStart:resetAnimate, onComplete:playAnimate });

							if(pageID==pageTotal){
								root.arrow.gotoAndPlay(2);
							}
						}
					}

					//swipe down
					if(deltaY>0){
						if(pageID>1){
							if(pageID==4){
								root.arrow.gotoAndPlay(72);
							}
							pageID--;
							TweenMax.to(container, duration, { y:-(pageID-1)*stageHeight, ease:Cubic.easeOut, overwrite:1, onStart:resetAnimate, onComplete:playAnimate });
						}
					}

				});

				function resetAnimate(){
					pages["p"+pageID].ani.gotoAndStop(0);
				}

				function playAnimate(){
					pages["p"+pageID].ani.gotoAndPlay(1);
				}

			}, 0);
		}

		// actions tween:
		this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(1));

		// 图层 8
		this.arrow = new lib.arrowdo();
		this.arrow.setTransform(355.7,1083.5,1,1,0,0,0,14.5,11);

		this.instance = new lib.bgtest();

		this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.instance},{t:this.arrow}]}).wait(1));

	}).prototype = p = new cjs.MovieClip();
	p.nominalBounds = new cjs.Rectangle(375,602.5,750,1205);

})(lib = lib||{}, images = images||{}, createjs = createjs||{}, ss = ss||{});
var lib, images, createjs, ss;