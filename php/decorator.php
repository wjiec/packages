<?php

// 1. explicit decorate
$router_node = new RouterRuleNode();

$get_method = new RouterGetMethodDecorator();
$get_method->decorate($router_node);

$post_method = new RouterGetMethodDecorator();
$get_method->decorate($router_node);

$uri = new RouterUriDecorator('api@v(versiond)@$module');
$uri->decorate($router_node);

$hook = new RouterAuthHookDecorator(new RouterCallback(Routerget_hook('auth')));
$hook->decorate($router_node);

// 2. implicit decorate
$router_node = new RouterRuleNode();

$router_node = new RouterGetMethodDecorator($router_node);
$router_node = new RouterPostMethodDecorator($router_node);
$router_node = new RouterUriDecorator($router_node, 'articles$action@w+.html@');
$router_node = new RouterHookDecorator($router_node, 'articles$action@w+.html@');
