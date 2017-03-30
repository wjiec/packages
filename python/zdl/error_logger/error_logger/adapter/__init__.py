#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
from error_logger.adapter import postgresql
from error_logger.utils import exceptions

global_vars = locals()

def create_connection(adapter, *args, **kwargs):
    if not isinstance(adapter, str):
        raise exceptions.FatalError('adapter except str type, got {}'.format(
            type(adapter)))
    adapter = global_vars.get(adapter)
    if adapter:
        try:
            _groups = adapter.__name__.split('.')
            adapter_class_name = ''.join([_groups[-1].capitalize(), 'Adapter'])
            adapter_class = getattr(adapter, adapter_class_name)
            return adapter_class(*args, **kwargs)
        except Exception:
            raise exceptions.FatalError('Adapter configure error')

