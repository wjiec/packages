<?php
/**
 * Generate share action
 */


class Rtfd_Action_GenerateShare extends Rtfd_Abstract_Action {
    /**
     * generate share url
     */
    public function start()
    {
        $create_time = time();
        $share_paths = $this->get_option('sp');
        $share_expired = intval($this->get_option('expired'));

        if ($share_expired !== 0 && $share_expired <= $create_time) {
            Rtfd_Request::abort(400, array(
                'errno' => 400,
                'error' => 'expired time invalid'
            ));
        }

        if (!is_array($share_paths)) {
            $share_paths = json_decode($share_paths, true);
            if ($share_paths === null) {
                Rtfd_Request::abort(400, array(
                    'errno' => 400,
                    'error' => 'share paths invalid'
                ));
            }
        }
        $share_paths = urlencode(json_encode($share_paths));
        if (preg_match('/share_(\w{32})/', $share_paths, $result)) {
            $share_code = $result;
        } else {
            $share_code = self::_generate_code($create_time, $share_expired, $share_paths);
        }
        Rtfd_Response::debug_output($share_code);

        $helper = new Rtfd_Helper_Database();
        $result = $helper->query(
            "insert into `share`
                    (`code`, `paths`, `created`, `expired`)
                  values
                    ('{$share_code}', '{$share_paths}', '{$create_time}', '{$share_expired}')
                  ;"
        );

        if ($result === false) {
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'generate share error'
            ));
        }

        Rtfd_Response::json_response(array(
            'status' => 0,
            'url' => self::_generate_url($share_code)
        ));
    }

    private static function _generate_code($create, $expired, $paths) {
        return md5(join('+', array(
            strval($create),
            strval($expired),
            sha1($paths)
        )));
    }

    private static function _generate_url($code) {
        return "/#/share_{$code}@!";
    }
}
