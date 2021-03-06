<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<div id="jumbo" class="jumbotron  sticky-top">
	<button data-toggle="collapse" onclick="toggleFilter()"
		data-target="#filterCriteria" aria-expanded="false">Show/Hide
		filter</button>
	<div id="filterCriteria" class="container collapse">
		<div class="row">
			<div class="col-sm-2 border">

				<div class="radio">
					<label><input type="radio" name="photo_filter" value="yes">
						Has photo</label>
				</div>
				<div class="radio">
					<label><input type="radio" name="photo_filter" value="no">
						Has no photo</label>
				</div>
				<div class="radio">
					<label><input type="radio" name="photo_filter" value=""
						checked> Doesn't Matter</label>
				</div>
			</div>
			<div class="col-sm-1"></div>
			<div class="col-sm-2 border">

				<div class="radio">
					<label><input type="radio" name="favourite_filter"
						value="yes"> Favourite</label>
				</div>
				<div class="radio">
					<label><input type="radio" name="favourite_filter"
						value="no"> Not Favourite</label>
				</div>
				<div class="radio">
					<label><input type="radio" name="favourite_filter" value=""
						checked> Doesn't Matter</label>
				</div>
			</div>
			<div class="col-sm-1"></div>
			<div class="col-sm-2 border">

				<div class="radio">
					<label><input type="radio" name="contacts_exchanged_filter"
						value="yes"> In Contact</label>
				</div>
				<div class="radio">
					<label><input type="radio" name="contacts_exchanged_filter"
						value="no"> Not In Contact</label>
				</div>
				<div class="radio">
					<label><input type="radio" name="contacts_exchanged_filter"
						value="" checked> Doesn't Matter</label>
				</div>
			</div>
			<div class="col-sm-1"></div>
			<div class="col-lg-3 border">
				<div class="container">
					<div class="row">
						<div class="col-sm-12 text-left">Distance within</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="d-flex justify-content-left my-4">
								<input type="text" id="distanceTextInput" value="30 km" readonly
									size="6"> <input name="distance_filter" value="300"
									class="border-0" type="range" min="30" max="300"
									data-show-value="true"
									onchange="updateDistanceText(this.value)"> <span
									class="font-weight-bold indigo-text ml-2 mt-1">300 km</span>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<br />
			</div>
		</div>
		<div class="row">

			<div class="col-sm-3 border">
				<label>Compatibility:</label> <select id="compatibility_filter"
					class="form-control" name="compatibility_filter">
					<option value="-1">doesn't matter</option>
					<option value="0.01">1 to 9%</option>
					<option value="0.1">10 to 29%</option>
					<option value="0.2">20 to 30%</option>
					<option value="0.3">30 to 39%</option>
					<option value="0.4">40 to 49%</option>
					<option value="0.5">50 to 59%</option>
					<option value="0.6">60 to 69%</option>
					<option value="0.7">70 to 79%</option>
					<option value="0.8">80 to 89%</option>
					<option value="0.9">90 to 99%</option>

				</select>
			</div>
			<div class="col-sm-3 border">
				<label>Age:</label> <select id="age_filter" class="form-control"
					name="age_filter">
					<option value="-1">doesn't matter</option>
					<option value="18">18 to 25 years</option>
					<option value="26">26 to 33 years</option>
					<option value="34">34 to 41 years</option>
					<option value="42">42 to 49 years</option>
					<option value="50">50 to 57 years</option>
					<option value="58">58 to 65 years</option>
					<option value="66">66 to 73 years</option>
					<option value="74">74 to 81 years</option>
					<option value="82">82 to 89 years</option>
					<option value="90">90 to 95 years</option>


				</select>
			</div>
			<div class="col-sm-3 border">
				<label>Height:</label> <select id="height_filter"
					class="form-control" name="height_filter">
					<option value="-1">doesn't matter</option>
					<option value="135">135 to 144 cm</option>
					<option value="145">145 to 154 cm</option>
					<option value="155">155 to 164 cm</option>
					<option value="165">165 to 174 cm</option>
					<option value="175">175 to 184 cm</option>
					<option value="185">185 to 194 cm</option>
					<option value="195">195 to 204 cm</option>
					<option value="205">205 to 210 cm</option>

				</select>
			</div>
			<div class="col-sm-3 border">
				<label>Religion:</label> <select id="religion_filter"
					class="form-control" name="religion_filter">
					<option value="">Doesn't Matter</option>
					<c:forEach items="${religions}" var="religion">

						<option value="${religion}">${religion}</option>

					</c:forEach>
				</select>
			</div>

		</div>
		<div class="row">
			<div class="col-sm-12">
				<br />
			</div>
		</div>
		<div class="row">
			<div class="col-sm-5"></div>
			<div id="filter_button" class="col-sm-2">
				<button name="filter" type="button" class="btn btn-primary btn-lg">Search</button>
			</div>
			<div class="col-sm-5"></div>
		</div>
	</div>
</div>