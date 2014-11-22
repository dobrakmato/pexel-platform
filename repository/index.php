<?php 
/*
 * Pexel Project - Minecraft minigame server platform.
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 *
 * This file is part of Pexel.
 *
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */

// Define version of repo script.
define("VERSION", "1.0");

// Fucntion that will build list.
function buildList($directory) {
	$files = array_diff(scandir($directory), array('..', '.'));
	echo json_encode(array_values($files));
}

// Conditions to return what user wanted.
if(isset($_GET['plugins'])) {
	// List of plugins.
	buildList(dirname(__FILE__) . '/plugins');
} else if (isset($_GET['maps'])) {
	// List of maps.
	buildList(dirname(__FILE__)  . '/maps');
} else if (isset($_GET['version'])) {
	// Version of script.
	echo VERSION;
} else {
	// Invalid request.
	header('HTTP/1.1 400 Bad Request', true, 400);
}