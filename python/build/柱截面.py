# -*- coding: utf-8 -*-
#!/usr/bin/env python
#
# Copyright (C) 2016 ShadowMan
#
import math
import tkinter as tk
import tkinter.ttk as ttk
import xlsxwriter
from collections import OrderedDict
import tkinter.messagebox as MB

class SlabLoadCalc(tk.Frame):

    def __init__(self, parent):
        tk.Frame.__init__(self, parent)
        self.master.wm_state('zoomed')
        self.workbook = xlsxwriter.Workbook('lpj.xlsx')

        self.pj_table = OrderedDict([
            (509, (2, 18)), (628, (2, 20)), (763, (3, 18)),
            (1017, (4, 18)), (1256, (4, 20)), (1272, (5, 18)),
            (1520, (4, 22)), (1527, (6, 18)), (1570, (5, 20)),
            (1884, (6, 20)), (1900, (5, 22)), (1964, (4, 25)),
            (2281, (6, 22)), (2454, (5, 25)), (2463, (4, 28)),
            (2945, (6, 25)), (3079, (5, 28)), (3695, (6, 28)),
        ])

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Beam Calc')
        self.pack(fill = tk.BOTH, expand = True)

        self.__init_window_position()

        self.__init_grid(50, 10)
        self.__init_style()

        label_1 = tk.Label(self, text = '恒荷载')
        label_1.grid(row = 2, column = 0)
        self.__init_hz()

        label_2 = tk.Label(self, text = '活荷载')
        label_2.grid(row = 7, column = 0)
        self.__init_h_z()

        label_3 = tk.Label(self, text = '地震作用')
        label_3.grid(row = 12, column = 0)
        self.__init_y_dz()

        self.__init_other_params()
        self.__init_result_params()
        self.__init_bot_btn()
        # self.__init_m()

    def __init_m(self):
        hhz_value = [
            (0, -81.8),
            (1, 81.6),
            (2, -82)
        ]
        list(map(lambda k: self.hhz_top_m[k[0]].set(k[1]), hhz_value))

        h_hz_value = [
            (0, -15.6),
            (1, 12.5),
            (2, -12.3)
        ]
        list(map(lambda k: self.h_hz_top_m[k[0]].set(k[1]), h_hz_value))

        dz_value = [
            (0, 398.7),
            (1, 41.2),
            (2, -316.2)
        ]
        list(map(lambda k: self.y_dz_top_m[k[0]].set(k[1]), dz_value))

        self.var_l_h.set(800)
        self.var_l_b.set(400)
        self.var_l_h_.set(100)
        self.var_l_b_.set(2500)

        self.hhz_v[0].set(80.3)
        self.hhz_v[1].set(-80.4)

        self.h_hz_v[0].set(11.8)
        self.h_hz_v[1].set(-10.8)

        self.y_dz_v[0].set(-95.3)
        self.y_dz_v[1].set(-95.3)

    def __init_hz(self):
        self.hhz_top_m = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.hhz_v = (tk.StringVar(), tk.StringVar())

        self.__init_beam_top_input(1, self.hhz_top_m)
        self.__init_beam_canvas(2)
        self.__init__beam_bot_input(3, self.hhz_v)

    def __init_h_z(self):
        self.h_hz_top_m = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.h_hz_v = (tk.StringVar(), tk.StringVar())

        self.__init_beam_top_input(6, self.h_hz_top_m)
        self.__init_beam_canvas(7)
        self.__init__beam_bot_input(8, self.h_hz_v)

    def __init_y_dz(self):
        self.y_dz_top_m = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.y_dz_v = (tk.StringVar(), tk.StringVar())

        self.__init_beam_top_input(11, self.y_dz_top_m)
        self.__init_beam_canvas(12)
        self.__init__beam_bot_input(13, self.y_dz_v)

    def __init_grid(self, row, colunm):
        for row_index in range(row):
            self.rowconfigure(row_index, pad = 3, weight = True)

        for column_index in range(colunm):
            self.columnconfigure(column_index, pad = 3, weight = True)

    def __init_beam_top_input(self, row, three_m):
        tl_frame = tk.Frame(self)
        tl_label = tk.Label(tl_frame, text = 'Left M')
        tl_entry = tk.Entry(tl_frame, textvariable = three_m[0])
        tl_label.pack(side = tk.TOP, expand = True)
        tl_entry.pack(side = tk.TOP, expand = True)
        tl_frame.grid(row = row, column = 1)

        tc_frame = tk.Frame(self)
        tc_label = tk.Label(tc_frame, text = 'Middle M')
        tc_entry = tk.Entry(tc_frame, textvariable = three_m[1])
        tc_label.pack(side = tk.TOP, expand = True)
        tc_entry.pack(side = tk.TOP, expand = True)
        tc_frame.grid(row = row, column = 5)

        tr_frame = tk.Frame(self)
        tr_label = tk.Label(tr_frame, text = 'Right M')
        tr_entry = tk.Entry(tr_frame, textvariable = three_m[2])
        tr_label.pack(side = tk.TOP, expand = True)
        tr_entry.pack(side = tk.TOP, expand = True)
        tr_frame.grid(row = row, column = 9)

    def __init_beam_canvas(self, row):
        frame = tk.Frame(self, relief = tk.RAISED)

        entry = tk.Entry(frame, state = 'readonly', width = 300, bd = 2, relief = tk.GROOVE)
        entry.pack(fill = tk.X, expand = True)

        frame.grid(row = row, column = 1, columnspan = 9)

    def __init__beam_bot_input(self, row, three_m):
        bl_frame = tk.Frame(self)
        bl_label = tk.Label(bl_frame, text = 'Left V')
        bl_entry = tk.Entry(bl_frame, textvariable = three_m[0])
        bl_label.pack(side = tk.TOP, expand = True)
        bl_entry.pack(side = tk.TOP, expand = True)
        bl_frame.grid(row = row, column = 1)

        br_frame = tk.Frame(self)
        br_label = tk.Label(br_frame, text = 'Right V')
        br_entry = tk.Entry(br_frame, textvariable = three_m[1])
        br_label.pack(side = tk.TOP, expand = True)
        br_entry.pack(side = tk.TOP, expand = True)
        br_frame.grid(row = row, column = 9)

    def __init_other_params(self):
        self.var_l_bh = tk.StringVar()
        self.__create_input_group('Beam BH', self.var_l_bh, 16, 1)

        self.var_fc = tk.StringVar()
        self.__create_input_group('Fc', self.var_fc, 16, 2)
        self.var_fc.set('14.3')

        self.var_fy = tk.StringVar()
        self.__create_input_group('Fy', self.var_fy, 16, 3)
        self.var_fy.set('360')

        self.var_l_h = tk.StringVar()
        self.__create_input_group('h', self.var_l_h, 16, 4)

        self.var_l_b = tk.StringVar()
        self.__create_input_group('b', self.var_l_b, 16, 5)

        self.var_l_h_ = tk.StringVar()
        self.__create_input_group('h_', self.var_l_h_, 16, 6)

        self.var_l_b_ = tk.StringVar()
        self.__create_input_group('b_', self.var_l_b_, 16, 7)

        self.var_l_ft = tk.StringVar()
        self.__create_input_group('Ft', self.var_l_ft, 16, 8)
        self.var_l_ft.set('1.43')

        self.var_l_as = tk.StringVar()
        self.__create_input_group('as', self.var_l_as, 16, 9)
        self.var_l_as.set('40')

    def __create_input_group(self, label, text_var, row, column, readonly = False):
        frame = tk.Frame(self)

        label = tk.Label(frame, text = label)
        entry = tk.Entry(frame, textvariable = text_var)

        if readonly is True:
            entry['state'] = 'readonly'

        label.pack(side = tk.TOP)
        entry.pack(side = tk.TOP)

        frame.grid(row = row, column = column)

    def __init_bot_btn(self):
        calc_btn = tk.Button(self, text = 'Calc', command = self.calc)
        calc_btn.grid(row = 45, column = 2)

        export_btn = tk.Button(self, text = 'Export', command = self.export)
        export_btn.grid(row = 45, column = 4)

        exit_btn = tk.Button(self, text = 'Exit', command = self.quit)
        exit_btn.grid(row = 45, column = 6)

    def __init_result_params(self):
        self.rst_l_z_m = tk.StringVar()
        self.__create_input_group('Left Max M', self.rst_l_z_m, 17, 1, True)
        self.rst_l_f_m = tk.StringVar()
        self.__create_input_group('Left Min M', self.rst_l_f_m, 18, 1, True)

        self.rst_c_z_m = tk.StringVar()
        self.__create_input_group('Center Max M', self.rst_c_z_m, 17, 2, True)
        self.rst_c_f_m = tk.StringVar()
        self.__create_input_group('Center Min M', self.rst_c_f_m, 18, 2, True)

        self.rst_r_z_m = tk.StringVar()
        self.__create_input_group('Right Max M', self.rst_r_z_m, 17, 3, True)
        self.rst_r_f_m = tk.StringVar()
        self.__create_input_group('Right Min M', self.rst_r_f_m, 18, 3, True)

        self.rst_l_min_as = tk.StringVar()
        self.__create_input_group('Left Top Min As', self.rst_l_min_as, 17, 4, True)
        self.rst_l_as = tk.StringVar()
        self.__create_input_group('Left Top As', self.rst_l_as, 18, 4, True)

        self.rst_r_min_as = tk.StringVar()
        self.__create_input_group('Right Top Min As', self.rst_r_min_as, 17, 5, True)
        self.rst_r_as = tk.StringVar()
        self.__create_input_group('Right Top As', self.rst_r_as, 18, 5, True)

        self.rst_bot_min_as = tk.StringVar()
        self.__create_input_group('Bot Min As', self.rst_bot_min_as, 17, 6, True)
        self.rst_bot_as = tk.StringVar()
        self.__create_input_group('Bot As', self.rst_bot_as, 18, 6, True)

        self.rst_fin_left_top_as = tk.StringVar()
        self.__create_input_group('Left Top As', self.rst_fin_left_top_as, 20, 1, True)
        self.rst_fin_left_bot_as = tk.StringVar()
        self.__create_input_group('Left Bot As', self.rst_fin_left_bot_as, 21, 1, True)


        self.rst_fin_center_bot_as = tk.StringVar()
        self.__create_input_group('Center Bot As', self.rst_fin_center_bot_as, 21, 2, True)

        self.rst_fin_right_top_as = tk.StringVar()
        self.__create_input_group('Right Top As', self.rst_fin_right_top_as, 20, 3, True)
        self.rst_fin_right_bot_as = tk.StringVar()
        self.__create_input_group('Right Bot As', self.rst_fin_right_bot_as, 21, 3, True)

    def calc(self):
        if self.var_l_bh.get() == '' or self.var_l_bh.get() is None:
            MB.showwarning('警告', '梁未命名')
            return

        # 恒荷载
        hhz_left_top_m = self.hhz_top_m[0].get()
        if not hhz_left_top_m:
            MB.showwarning('警告', '左端恒载弯矩值冲突')
            return
        hhz_left_m = float(hhz_left_top_m)

        hhz_center_top_m = self.hhz_top_m[1].get()
        if not hhz_center_top_m:
            MB.showwarning('警告', '跨中恒载弯矩值冲突')
            return
        hhz_center_m = float(hhz_center_top_m)


        hhz_right_top_m = self.hhz_top_m[2].get()
        if not hhz_right_top_m:
            MB.showwarning('警告', '右端恒载弯矩值冲突')
            return
        hhz_right_m = float(hhz_right_top_m)

        # 活载
        hz_left_top_m = self.h_hz_top_m[0].get()
        if not hz_left_top_m:
            MB.showwarning('警告', '左端活载弯矩值冲突')
            return
        hz_left_m = float(hz_left_top_m)

        hz_center_top_m = self.h_hz_top_m[1].get()
        if not hz_center_top_m:
            MB.showwarning('警告', '跨中活载弯矩值冲突')
            return
        hz_center_m = float(hz_center_top_m)

        hz_right_top_m = self.h_hz_top_m[2].get()
        if not hz_right_top_m:
            MB.showwarning('警告', '右端活载弯矩值冲突')
            return
        hz_right_m = float(hz_right_top_m)

        # 地震
        dz_left_top_m = self.y_dz_top_m[0].get()
        if not dz_left_top_m:
            MB.showwarning('警告', '左端地震作用弯矩值冲突')
            return
        dz_left_m = float(dz_left_top_m)

        dz_center_top_m = self.y_dz_top_m[1].get()
        if not dz_center_top_m:
            MB.showwarning('警告', '跨中地震作用弯矩值冲突')
            return
        dz_center_m = float(dz_center_top_m)

        dz_right_top_m = self.y_dz_top_m[2].get()
        if not dz_right_top_m:
            MB.showwarning('警告', '右端地震作用弯矩值冲突')
            return
        dz_right_m = float(dz_right_top_m)

        # 左端组合值
        fkz_zh_left_kb_m = 1.2 * hhz_left_m + 1.4 * hz_left_m
        fkz_zh_left_yj_m = 1.35 * hhz_left_m + 1.4 * 0.7 * hz_left_m
        kz_zh_left_z_m = 0.75 * ( 1.2 * (hhz_left_m + 0.5 * hz_left_m) + 1.3 * dz_left_m)
        kz_zh_left_f_m = 0.75 * ( 1.2 * (hhz_left_m + 0.5 * hz_left_m) - 1.3 * dz_left_m)

        left_max_z_m = round(max(fkz_zh_left_kb_m, fkz_zh_left_yj_m, kz_zh_left_z_m, kz_zh_left_f_m), 2)
        left_max_f_m = round(min(fkz_zh_left_kb_m, fkz_zh_left_yj_m, kz_zh_left_z_m, kz_zh_left_f_m), 2)

        self.rst_l_z_m.set(left_max_z_m)
        self.rst_l_f_m.set(left_max_f_m)

        # 跨中组合值
        fkz_zh_center_kb_m = 1.2 * hhz_center_m + 1.4 * hz_center_m
        fkz_zh_center_yj_m = 1.35 * hhz_center_m + 1.4 * 0.7 * hz_center_m
        kz_zh_center_z_m = 0.75 * ( 1.2 * (hhz_center_m + 0.5 * hz_center_m) + 1.3 * dz_center_m)
        kz_zh_center_f_m = 0.75 * ( 1.2 * (hhz_center_m + 0.5 * hz_center_m) - 1.3 * dz_center_m)

        center_max_z_m = round(max(fkz_zh_center_kb_m, fkz_zh_center_yj_m, kz_zh_center_z_m, kz_zh_center_f_m), 2)
        center_max_f_m = round(min(fkz_zh_center_kb_m, fkz_zh_center_yj_m, kz_zh_center_z_m, kz_zh_center_f_m), 2)

        self.rst_c_z_m.set(center_max_z_m)
        self.rst_c_f_m.set(center_max_f_m)

        # 右端组合值
        fkz_zh_right_kb_m = 1.2 * hhz_right_m + 1.4 * hz_right_m
        fkz_zh_right_yj_m = 1.35 * hhz_right_m + 1.4 * 0.7 * hz_right_m
        kz_zh_right_z_m = 0.75 * ( 1.2 * (hhz_right_m + 0.5 * hz_right_m) + 1.3 * dz_right_m)
        kz_zh_right_f_m = 0.75 * ( 1.2 * (hhz_right_m + 0.5 * hz_right_m) - 1.3 * dz_right_m)

        right_max_z_m = round(max(fkz_zh_right_kb_m, fkz_zh_right_yj_m, kz_zh_right_z_m, kz_zh_right_f_m), 2)
        right_max_f_m = round(min(fkz_zh_right_kb_m, fkz_zh_right_yj_m, kz_zh_right_z_m, kz_zh_right_f_m), 2)

        self.rst_r_z_m.set(right_max_z_m)
        self.rst_r_f_m.set(right_max_f_m)

        h = float(self.var_l_h.get())
        h_ = float(self.var_l_h_.get())
        b = float(self.var_l_b.get())
        b_ = float(self.var_l_b_.get())
        fy = float(self.var_fy.get())
        fc = float(self.var_fc.get())
        ft = float(self.var_l_ft.get())
        as_ = float(self.var_l_as.get())

        h0 = h - as_

        # 左端顶部配筋
        left_top_as = abs(round(left_max_f_m * math.pow(10, 6) / (fy * (h0 - as_)), 2))
        left_top_min_as = max(0.45 * ft / fy, 0.0025) * b_ * h_
        self.rst_l_min_as.set(left_top_min_as)
        self.rst_l_as.set(left_top_as)

        # 右端顶部配筋
        right_top_as = abs(round(right_max_f_m * math.pow(10, 6) / (fy * (h0 - as_)), 2))
        right_top_min_as = max(0.45 * ft / fy, 0.0025) * b_ * h_
        self.rst_r_min_as.set(right_top_min_as)
        self.rst_r_as.set(right_top_as)

        # 底部通长钢筋
        bot_max_m = abs(max(left_max_z_m, center_max_z_m, right_max_z_m))
        bot_x = h0 - math.sqrt(math.pow(h0, 2) - 2 * bot_max_m * 1000 / (fc * b_ / 1000))
        bot_as = round(fc * b_ * bot_x / fy, 2)
        bot_min_as = round(max(0.45 * ft / fy, 0.002) * b * h)
        self.rst_bot_min_as.set(bot_min_as)
        self.rst_bot_as.set(bot_as)

        list(map(lambda k: k.set(max(bot_min_as, bot_as)),
            [ self.rst_fin_left_bot_as, self.rst_fin_center_bot_as, self.rst_fin_right_bot_as ]))
        self.rst_fin_left_top_as.set(max(left_top_as, left_top_min_as))
        self.rst_fin_right_top_as.set(max(right_top_as, right_top_min_as))

        # export
        worksheet = self.workbook.add_worksheet()
        # merge format
        merge_format = self.workbook.add_format({
            'align': 'center',
            'valign': 'vcenter',
        })

        # title
        worksheet.merge_range('A1:J1', '梁配筋计算表', merge_format)

        worksheet.merge_range('A2:A4', '编号', merge_format)
        worksheet.merge_range('B2:B4', '支座截面', merge_format)
        worksheet.merge_range('C2:C4', '力的类型', merge_format)
        worksheet.merge_range('D2:F2', '荷载种类', merge_format)
        worksheet.merge_range('D3:D4', '恒载', merge_format)
        worksheet.merge_range('E3:E4', '活载', merge_format)
        worksheet.merge_range('F3:F4', '地震作用', merge_format)
        worksheet.merge_range('G2:J2', '组合值', merge_format)
        worksheet.merge_range('G3:H3', '非抗震', merge_format)
        worksheet.write('G4', '可变荷载控制', merge_format)
        worksheet.write('H4', '永久荷载控制', merge_format)
        worksheet.merge_range('I3:J3', '抗震', merge_format)
        worksheet.write('I4', '取+', merge_format)
        worksheet.write('J4', '取-', merge_format)
        worksheet.merge_range('B5:B6', '左端', merge_format)
        worksheet.write('B7', '跨中', merge_format)
        worksheet.merge_range('B8:B9', '右端', merge_format)
        worksheet.merge_range('A5:A9', self.var_l_bh.get(), merge_format)

        worksheet.write('C5', 'M', merge_format)
        worksheet.write('C6', 'V', merge_format)
        worksheet.write('C7', 'M', merge_format)
        worksheet.write('C8', 'M', merge_format)
        worksheet.write('C9', 'V', merge_format)

        # 恒荷载
        worksheet.write('D5', self.hhz_top_m[0].get(), merge_format)
        worksheet.write('D6', self.hhz_v[0].get(), merge_format)
        worksheet.write('D7', self.hhz_top_m[1].get(), merge_format)
        worksheet.write('D8', self.hhz_top_m[2].get(), merge_format)
        worksheet.write('D9', self.hhz_v[1].get(), merge_format)

        # 活荷载
        worksheet.write('E5', self.h_hz_top_m[0].get(), merge_format)
        worksheet.write('E6', self.h_hz_v[0].get(), merge_format)
        worksheet.write('E7', self.h_hz_top_m[1].get(), merge_format)
        worksheet.write('E8', self.h_hz_top_m[2].get(), merge_format)
        worksheet.write('E9', self.h_hz_v[1].get(), merge_format)

        # 地震作用
        worksheet.write('F5', self.y_dz_top_m[0].get(), merge_format)
        worksheet.write('F6', self.y_dz_v[0].get(), merge_format)
        worksheet.write('F7', self.y_dz_top_m[1].get(), merge_format)
        worksheet.write('F8', self.y_dz_top_m[2].get(), merge_format)
        worksheet.write('F9', self.y_dz_v[1].get(), merge_format)

        hhz_left_v = float(self.hhz_v[0].get())
        hhz_right_v = float(self.hhz_v[1].get())
        h_hz_left_v = float(self.h_hz_v[0].get())
        h_hz_right_v = float(self.h_hz_v[1].get())
        y_dz_left_v = float(self.y_dz_v[0].get())
        y_dz_right_v = float(self.y_dz_v[1].get())

        # 非抗震 左端 V组合
        fkz_zh_left_kb_v = 1.2 * hhz_left_v + 1.4 * h_hz_left_v
        fkz_zh_left_yj_v = 1.35 * hhz_left_v + 1.4 * 0.7 * h_hz_left_v

        # 非抗震 右端 V组合
        fkz_zh_right_kb_v = 1.2 * hhz_right_v + 1.4 * h_hz_right_v
        fkz_zh_right_yj_v = 1.35 * hhz_right_v + 1.4 * 0.7 * h_hz_right_v

        # 非抗震 可变
        worksheet.write('G5', fkz_zh_left_kb_m, merge_format)
        worksheet.write('G6', fkz_zh_left_kb_v, merge_format)
        worksheet.write('G7', fkz_zh_center_kb_m, merge_format)
        worksheet.write('G8', fkz_zh_right_kb_m, merge_format)
        worksheet.write('G9', fkz_zh_right_kb_v, merge_format)

        # 非抗震 永久
        worksheet.write('H5', fkz_zh_left_yj_m, merge_format)
        worksheet.write('H6', fkz_zh_left_yj_v, merge_format)
        worksheet.write('H7', fkz_zh_center_yj_m, merge_format)
        worksheet.write('H8', fkz_zh_right_yj_m, merge_format)
        worksheet.write('H9', fkz_zh_right_yj_v, merge_format)

        # 左端
        kz_zh_left_z_v = 0.75 * (1.2 * (hhz_left_v + 0.5 * h_hz_left_v) + 1.3 * y_dz_left_v)
        kz_zh_left_f_v = 0.75 * (1.2 * (hhz_left_v + 0.5 * h_hz_left_v) - 1.3 * y_dz_left_v)
        # 右端
        kz_zh_right_z_v = 0.75 * (1.2 * (hhz_right_v + 0.5 * h_hz_right_v) + 1.3 * y_dz_right_v)
        kz_zh_right_f_v = 0.75 * (1.2 * (hhz_right_v + 0.5 * h_hz_right_v) - 1.3 * y_dz_right_v)

        # 抗震 取正
        worksheet.write('I5', kz_zh_left_z_m, merge_format)
        worksheet.write('I6', kz_zh_left_z_v, merge_format)
        worksheet.write('I7', kz_zh_center_z_m, merge_format)
        worksheet.write('I8', kz_zh_right_z_m, merge_format)
        worksheet.write('I9', kz_zh_right_z_v, merge_format)

        # 抗震 取负
        worksheet.write('J5', kz_zh_left_f_m, merge_format)
        worksheet.write('J6', kz_zh_left_f_v, merge_format)
        worksheet.write('J7', kz_zh_center_f_m, merge_format)
        worksheet.write('J8', kz_zh_right_f_m, merge_format)
        worksheet.write('J9', kz_zh_right_f_v, merge_format)

        # 其他参数
        worksheet.write('A11', 'Fc', merge_format)
        worksheet.write('B11', fc, merge_format)
        worksheet.write('C11', 'Ft', merge_format)
        worksheet.write('D11', ft, merge_format)
        worksheet.write('E11', 'Fy', merge_format)
        worksheet.write('F11', fy, merge_format)
        worksheet.write('G11', 'h', merge_format)
        worksheet.write('H11', h, merge_format)
        worksheet.write('I11', 'b', merge_format)
        worksheet.write('J11', b, merge_format)
        worksheet.write('A12', 'h\'', merge_format)
        worksheet.write('B12', h_, merge_format)
        worksheet.write('C12', 'b\'', merge_format)
        worksheet.write('D12', b_, merge_format)
        worksheet.write('E12', 'as', merge_format)
        worksheet.write('F12', as_, merge_format)
        worksheet.write('G12', 'h0', merge_format)
        worksheet.write('H12', h0, merge_format)

        # 弯矩结论
        worksheet.write('A14', '左端最大正弯矩', merge_format)
        worksheet.write('B14', left_max_z_m, merge_format)
        worksheet.write('C14', '跨中最大正弯矩', merge_format)
        worksheet.write('D14', center_max_z_m, merge_format)
        worksheet.write('E14', '右端最大正弯矩', merge_format)
        worksheet.write('F14', right_max_z_m, merge_format)
        worksheet.write('G14', '左端顶部最小配筋', merge_format)
        worksheet.write('H14', left_top_min_as, merge_format)
        worksheet.write('I14', '右端顶部最小配筋', merge_format)
        worksheet.write('J14', right_top_min_as, merge_format)
        # Line 15
        worksheet.write('A15', '左端最大负弯矩', merge_format)
        worksheet.write('B15', left_max_f_m, merge_format)
        worksheet.write('C15', '跨中最大负弯矩', merge_format)
        worksheet.write('D15', center_max_f_m, merge_format)
        worksheet.write('E15', '右端最大负弯矩', merge_format)
        worksheet.write('F15', right_max_f_m, merge_format)
        worksheet.write('G15', '左端顶部计算配筋', merge_format)
        worksheet.write('H15', left_top_as, merge_format)
        worksheet.write('I15', '右端顶部计算配筋', merge_format)
        worksheet.write('J15', right_top_as, merge_format)
        
        left_top_xp = max(left_top_as, left_top_min_as)
        left_top_sp = self.__find_pj_plan(left_top_xp)

        right_top_xp = max(right_top_as, right_top_min_as)
        right_top_sp = self.__find_pj_plan(right_top_xp)

        # Line 16
        worksheet.write('A17', '左端顶部需配', merge_format)
        worksheet.write('B17', left_top_xp, merge_format)
        worksheet.write('C17', '跨中顶部需配', merge_format)
        worksheet.write('D17', '', merge_format) # 跨中顶部采用通长筋拉通
        worksheet.write('E17', '右端顶部需配', merge_format)
        worksheet.write('F17', right_top_xp, merge_format)
        worksheet.write('G17', '左端顶部实配', merge_format)
        worksheet.write('H17', '{}Φ{} / {}'.format(*left_top_sp[0], left_top_sp[1]), merge_format)
        worksheet.write('I17', '右端顶部实配', merge_format)
        worksheet.write('J17', '{}Φ{} / {}'.format(*right_top_sp[0], right_top_sp[1]), merge_format)
        
        bot_xp = max(bot_as, bot_min_as)
        bot_sp = self.__find_pj_plan(bot_xp)

        # Line 18
        worksheet.write('A18', '左端底部需配', merge_format)
        worksheet.write('B18', bot_xp, merge_format)
        worksheet.write('C18', '跨中底部需配', merge_format)
        worksheet.write('D18', bot_xp, merge_format)
        worksheet.write('E18', '右端底部需配', merge_format)
        worksheet.write('F18', bot_xp, merge_format)
        worksheet.write('G18', '左端底部实配', merge_format)
        worksheet.write('H18', '{}Φ{} / {}'.format(*bot_sp[0], bot_sp[1]), merge_format)
        worksheet.write('I18', '右端底部实配', merge_format)
        worksheet.write('J18', '{}Φ{} / {}'.format(*bot_sp[0], bot_sp[1]), merge_format)

        kz_zh_left_z_m_no_yre = kz_zh_left_z_m / 0.75
        kz_zh_left_f_m_no_yre = kz_zh_left_f_m / 0.75
        kz_zh_right_z_m_no_yre = kz_zh_right_z_m / 0.75
        kz_zh_right_f_m_no_yre = kz_zh_right_f_m / 0.75

        left_top_no_yre_max_m = max(kz_zh_left_z_m_no_yre, fkz_zh_left_kb_m, fkz_zh_left_yj_m, kz_zh_left_f_m_no_yre)
        left_bot_no_yre_max_m = min(kz_zh_left_f_m_no_yre, fkz_zh_left_kb_m, fkz_zh_left_yj_m, kz_zh_left_z_m_no_yre)

        right_top_no_yre_max_m = max(kz_zh_right_z_m_no_yre, fkz_zh_right_kb_m, fkz_zh_right_yj_m, kz_zh_right_f_m_no_yre)
        right_bot_no_yre_max_m = min(kz_zh_right_f_m_no_yre, fkz_zh_right_kb_m, fkz_zh_right_yj_m, kz_zh_right_z_m_no_yre)

        shz_m = round(abs(left_top_no_yre_max_m) + abs(right_bot_no_yre_max_m), 2)
        nsz_m = round(abs(left_bot_no_yre_max_m) + abs(right_top_no_yre_max_m), 2)

        worksheet.merge_range('A19:J20', '梁的配箍计算(以下弯矩未经过承载力抗震调整系数γre调整)', merge_format)
        worksheet.write('A21', '左端最大正弯矩', merge_format)
        worksheet.write('B21', left_top_no_yre_max_m, merge_format)
        worksheet.write('C21', '右端最大正弯矩', merge_format)
        worksheet.write('D21', right_top_no_yre_max_m, merge_format)
        worksheet.write('E21', '顺时针', merge_format)
        worksheet.merge_range('F21:G21', 'Ml + Mr = {}'.format(shz_m), merge_format)

        worksheet.write('A22', '左端最大负弯矩', merge_format)
        worksheet.write('B22', left_bot_no_yre_max_m, merge_format)
        worksheet.write('C22', '右端最大负弯矩', merge_format)
        worksheet.write('D22', right_bot_no_yre_max_m, merge_format)
        worksheet.write('E22', '逆时针', merge_format)
        worksheet.merge_range('F22:G22', 'Ml + Mr = {}'.format(nsz_m), merge_format)

        max_no_yre_m = round(max(shz_m, nsz_m), 2)

        worksheet.merge_range('I21:I22', '取最大弯矩', merge_format)
        worksheet.merge_range('J21:J22', max_no_yre_m, merge_format)

        ln = b_ * 3
        v_gb = max(abs(1.2 * (hhz_left_v + 0.5 * h_hz_left_v)), abs(1.2 * (hhz_right_v + 0.5 * h_hz_right_v)))
        v_b = round(1.2 * (max_no_yre_m / (ln / 1000)) + v_gb, 2)

        tz_v_b = 0.85 * v_b

        worksheet.write('A24', 'Vgb', merge_format)
        worksheet.write('B24', v_gb, merge_format)
        worksheet.write('C24', 'Vb', merge_format)
        worksheet.merge_range('D24:G24', '1.2*({}/{})+{}={}'.format(max_no_yre_m, ln / 1000, v_gb, v_b), merge_format)
        worksheet.write('H24', 'γre * Vb', merge_format)
        worksheet.merge_range('I24:J24', '0.85*{}={}'.format(v_b, tz_v_b), merge_format)


        test_v = 0.25 * 1.0 * fc * b * h0
        if (test_v < tz_v_b * 1000):  
            MB.showwarning('警告', '截面尺寸不符合要求,请重新设计截面[滑稽]')
            return
        worksheet.merge_range('A26:D26', '0.25*βcfcbh0 = {}N > V = {}N'.format(test_v, tz_v_b * 1000), merge_format)
        worksheet.merge_range('E26:F26', '截面尺寸满足要求', merge_format)

        test_js_pg = round((tz_v_b * 1000) / (ft * b * h0), 2)
        worksheet.merge_range('G26:I26', 'V/ftbh0={} {} 1.0'.format(test_js_pg, '>' if test_js_pg > 1.0 else '<'), merge_format)
        if test_js_pg < 1.0:
            worksheet.write('J26', '构造配箍', merge_format)

            worksheet.write('A28', '-', merge_format)
            worksheet.merge_range('B28:E28', '选用双肢箍(n = 2)Φ6(Asv1 = 28.27mm2)', merge_format)
            worksheet.merge_range('G28:J28', '非加密区s = 200mm, 加密区s = 100mm', merge_format)
        else:
            worksheet.write('J26', '计算配箍', merge_format)

            a_sv_d_s = round((tz_v_b * 1000 - 0.7 * ft * b * h0) / (270 * h0), 3)

            worksheet.write('A28', 'Asv/s={}'.format(a_sv_d_s), merge_format)
            worksheet.merge_range('B28:E28', '选用双肢箍(n = 2)Φ8(Asv1 = 50.3mm2)', merge_format)
            worksheet.merge_range('G28:J28', '非加密区s = 200mm, 加密区s = 100mm', merge_format)


    def __find_pj_plan(self, xp):
        sp = None
        for pj_mj in self.pj_table:
            if pj_mj > xp:
                sp = self.pj_table[pj_mj]
                break;
        else:
            sp = self.pj_table[pj_mj]
        return (sp, pj_mj)

    def export(self):
        self.workbook.close()

    def __init_style(self):
        self.style = ttk.Style()
        self.style.configure("TButton", padding = (0, 5, 0, 5))

    def __init_window_position(self, width = None, height = None):
        screen_width = self.master.winfo_screenwidth()
        screen_height = self.master.winfo_screenheight()

        if width is None:
            width = screen_width
        if height is None:
            height = screen_height

        x_coorindate = int((screen_width - width) / 2)
        y_coordinate = int((screen_height - height) / 2)

        self.master.geometry('{}x{}+{}+{}'.format(width, height, x_coorindate, y_coordinate))

if __name__ == '__main__':
    root = tk.Tk()
    slab_load_calc = SlabLoadCalc(root)
    root.mainloop()
