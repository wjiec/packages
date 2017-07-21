<?php
/**
 * Reade the Fuck Docs Entry
 *
 * @package rtfd
 * @author ShadowMan
 */

define('DEBUG_MODE', true);
//define('SKIP_SIGNATURE', true);
define('SKIP_REQUEST_EXPIRED', true);


// root directory
define('_RTFD_ROOT_DIRECTORY_', dirname(__FILE__));

// Documents directory
define('_RTFD_DOCUMENTS_DIRECTORY_', '/Docs');

// RTFD service directory
define('_RTFD_SERVICE_DIRECTORY_', '/Service');

// reset include_path
set_include_path(get_include_path() . PATH_SEPARATOR
    . _RTFD_ROOT_DIRECTORY_ . _RTFD_DOCUMENTS_DIRECTORY_ . PATH_SEPARATOR
    . _RTFD_ROOT_DIRECTORY_ . _RTFD_SERVICE_DIRECTORY_ . PATH_SEPARATOR
);

// include Core module
require_once 'Rtfd/Core.php';

// initializing environments
Rtfd_Core::init_environments();

// Start Service
Rtfd_Core::start_service();
