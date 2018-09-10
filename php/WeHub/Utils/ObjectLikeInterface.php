<?php
/**
 * ObjectLikeInterface.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Utils;


/**
 * Interface ObjectLikeInterface
 * @package WeHub\Utils
 */
interface ObjectLikeInterface {

    /**
     * @return array
     */
    public function toObject(): array;

}
