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

        self.master.wm_state( 'zoomed' )

        self.workbook = xlsxwriter.Workbook('lb.xlsx')
        self.auto_inc = 1

        self._kx2_ky2_lib = {
            '0.45': (0.0994, 0.0335),
            '0.50': (0.0994, 0.0335),
            '0.5': (0.0994, 0.0335),
            '0.55': (0.0927, 0.0359),
            '0.6': (0.0860, 0.0379),
            '0.60': (0.0860, 0.0379),
            '0.65': (0.0795, 0.0396),
            '0.7': (0.0732, 0.0410),
            '0.70': (0.0732, 0.0410),
            '0.75': (0.0673, 0.0420),
            '0.8': (0.0617, 0.0428),
            '0.80': (0.0617, 0.0428),
            '0.85': (0.0564, 0.0432),
            '0.9': (0.0516, 0.0434),
            '0.90': (0.0516, 0.0434),
            '0.95': (0.0471, 0.0432),
            '1': (0.0429, 0.0429),
            '1.0': (0.0429, 0.0429),
            '1.00': (0.0429, 0.0429)
        }

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Slab Load Calc')
        self.pack(fill = tk.BOTH, expand = True)

        self.__init_window_position()

        self.__init_grid()
        self.__init_style()
        self.__init_input_params()
        self.__init_result_ui()
        self.__init_calc_btn()

    def __init_grid(self):
        self.columnconfigure(0, pad = 3, weight = True)
        self.columnconfigure(1, pad = 3, weight = True)
        self.columnconfigure(2, pad = 3, weight = True)
        self.columnconfigure(3, pad = 3, weight = True)
        self.columnconfigure(4, pad = 3, weight = True)
        self.columnconfigure(5, pad = 3, weight = True)
        self.columnconfigure(6, pad = 3, weight = True)
        self.columnconfigure(7, pad = 3, weight = True)
        self.columnconfigure(8, pad = 3, weight = True)
        self.columnconfigure(9, pad = 3, weight = True)
        self.columnconfigure(10, pad = 3, weight = True)
        self.columnconfigure(11, pad = 3, weight = True)

        self.rowconfigure(0, pad = 3, weight = True)
        self.rowconfigure(1, pad = 3, weight = True)
        self.rowconfigure(2, pad = 3, weight = True)
        self.rowconfigure(3, pad = 3, weight = True)
        self.rowconfigure(4, pad = 3, weight = True)
        self.rowconfigure(5, pad = 3, weight = True)
        self.rowconfigure(6, pad = 3, weight = True)
        self.rowconfigure(7, pad = 3, weight = True)
        self.rowconfigure(8, pad = 3, weight = True)
        self.rowconfigure(9, pad = 3, weight = True)
        self.rowconfigure(10, pad = 3, weight = True)
        self.rowconfigure(11, pad = 3, weight = True)

    def __init_style(self):
        self.style = ttk.Style()
        self.style.configure("TButton", padding = (0, 5, 0, 5))

    def __init_input_params(self):
        self.var_g = tk.StringVar()
        v_input = self.__create_input('G', self.var_g, 0, 2, False)

        v_input.focus_set()


        self.var_q = tk.StringVar()
        self.__create_input('Q', self.var_q, 0, 3, False)

        self.var_lx = tk.StringVar()
        self.__create_input('Lx', self.var_lx, 0, 4, False, self.calc_lx_d_ly)

        self.var_ly = tk.StringVar()
        self.__create_input('Ly', self.var_ly, 0, 5, False, self.calc_lx_d_ly)

        self.var_h = tk.StringVar()
        self.__create_input('H', self.var_h, 0, 6, False)
        self.var_h.set(100)

        self.var_fc = tk.StringVar()
        self.__create_input('Fc', self.var_fc, 0, 7, False)
        self.var_fc.set(14.3)

        self.var_ft = tk.StringVar()
        self.__create_input('Ft', self.var_ft, 0, 8, False)
        self.var_ft.set(1.43)

        self.var_fy = tk.StringVar()
        self.__create_input('Fy', self.var_fy, 0, 9, False)
        self.var_fy.set(300)

        self.var_eb = tk.StringVar()
        self.__create_input('Eb', self.var_eb, 0, 10, False)
        self.var_eb.set(0.55)

        self.var_lx_d_ly_real = tk.StringVar()
        self.__create_input('Lx/Ly', self.var_lx_d_ly_real, 1, 2)

        self.var_lx_d_ly = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.__create_k_val('Lx/Ly Range', self.var_lx_d_ly, 1, 3, False, self.validate_kx2, self.validate_ky2)

        self.var_kx1 = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.__create_k_val('Kx1', self.var_kx1, 1, 4)

        self.var_ky1 = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.__create_k_val('Ky1', self.var_ky1, 1, 5)

        self.var_kx0 = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.__create_k_val('Kx0', self.var_kx0, 1, 6)

        self.var_ky0 = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.__create_k_val('Ky0', self.var_ky0, 1, 7)

        self.var_kx2 = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.__create_k_val('Kx2', self.var_kx2, 1, 8, True)

        self.var_ky2 = (tk.StringVar(), tk.StringVar(), tk.StringVar())
        self.__create_k_val('Ky2', self.var_ky2, 1, 9, True)

    def calc_lx_d_ly(self):
        lx = self.var_lx.get()
        ly = self.var_ly.get()

        if lx and ly:
            value = round(float(lx) / float(ly), 2)

            if value < 0.5 or value > 1.0:
                MB.showerror('Error', 'Lx / Ly 数值超出范围')
            else:
                self.var_lx_d_ly_real.set(str(value))

                top = round(value - value % 0.05, 2)
                bot = round(top + 0.05, 2)

                if round(top % 0.1, 2) == 0.1:
                    top = str(top) + '0'

                if round(bot % 0.1, 2) == 0.1:
                    bot = str(bot) + '0'

                self.var_lx_d_ly[0].set(top)
                self.var_lx_d_ly[1].set(bot)

        return True

    def validate_kx2(self):
        if self.var_lx_d_ly[0].get() in self._kx2_ky2_lib:
            value = self._kx2_ky2_lib[self.var_lx_d_ly[0].get()]

            self.var_kx2[0].set(value[0])
            self.var_ky2[0].set(value[1])

        return True

    def validate_ky2(self):
        if self.var_lx_d_ly[1].get() in self._kx2_ky2_lib:
            value = self._kx2_ky2_lib[self.var_lx_d_ly[1].get()]

            self.var_kx2[1].set(value[0])
            self.var_ky2[1].set(value[1])

        return True

    def __init_result_ui(self):
        label = tk.Label(self, text = 'Result')
        label.grid(column = 0, row = 2, rowspan = 6)

        label = tk.Label(self, text = '跨中')
        label.grid(column = 1, row = 3, rowspan = 2)

        label = tk.Label(self, text = '支座')
        label.grid(column = 1, row = 5, rowspan = 2)

        label = tk.Label(self, text = '弯矩')
        label.grid(column = 1, row = 2)

        self.rst_m_x1 = tk.StringVar()
        self.__create_input('Mx1', self.rst_m_x1, 2, 2)

        self.rst_m_y1 = tk.StringVar()
        self.__create_input('My1', self.rst_m_y1, 2, 3)

        self.rst_m_x2 = tk.StringVar()
        self.__create_input('Mx2', self.rst_m_x2, 2, 4)

        self.rst_m_y2 = tk.StringVar()
        self.__create_input('My2', self.rst_m_y2, 2, 5)

        self.rst_m_x0 = tk.StringVar()
        self.__create_input('Mx0', self.rst_m_x0, 2, 6)

        self.rst_m_y0 = tk.StringVar()
        self.__create_input('My0', self.rst_m_y0, 2, 7)

        self.rst_m_x = tk.StringVar()
        self.__create_input('Mx', self.rst_m_x, 2, 8)

        self.rst_m_y = tk.StringVar()
        self.__create_input('My', self.rst_m_y, 2, 9)

        # result: KZ-lx
        self.rst_kz_lx_m = tk.StringVar()
        self.__create_input('Mx', self.rst_kz_lx_m, 3, 2)

        self.rst_kz_lx_h0 = tk.StringVar()
        self.__create_input('H0', self.rst_kz_lx_h0, 3, 3)

        self.rst_kz_lx_b = tk.StringVar()
        self.__create_input('B', self.rst_kz_lx_b, 3, 4)

        self.rst_kz_lx_x = tk.StringVar()
        self.__create_input('X', self.rst_kz_lx_x, 3, 5)

        self.rst_kz_lx_ebh0 = tk.StringVar()
        self.__create_input('EBH0', self.rst_kz_lx_ebh0, 3, 6)

        self.rst_kz_lx_as = tk.StringVar()
        self.__create_input('As', self.rst_kz_lx_as, 3, 7)

        self.rst_kz_lx_min = tk.StringVar()
        self.__create_input('P min', self.rst_kz_lx_min, 3, 8)

        self.rst_kz_lx_as_min = tk.StringVar()
        self.__create_input('As min', self.rst_kz_lx_as_min, 3, 9)

        self.rst_kz_lx_as_xp = tk.StringVar()
        self.__create_input('XP', self.rst_kz_lx_as_xp, 3, 10)

        self.rst_kz_lx_as_sp = tk.StringVar()
        self.__create_input('SP', self.rst_kz_lx_as_sp, 3, 11)

        # result: KZ-ly
        self.rst_kz_ly_m = tk.StringVar()
        self.__create_input('Mx', self.rst_kz_ly_m, 4, 2)

        self.rst_kz_ly_h0 = tk.StringVar()
        self.__create_input('H0', self.rst_kz_ly_h0, 4, 3)

        self.rst_kz_ly_b = tk.StringVar()
        self.__create_input('B', self.rst_kz_ly_b, 4, 4)

        self.rst_kz_ly_x = tk.StringVar()
        self.__create_input('X', self.rst_kz_ly_x, 4, 5)

        self.rst_kz_ly_ebh0 = tk.StringVar()
        self.__create_input('EBH0', self.rst_kz_ly_ebh0, 4, 6)

        self.rst_kz_ly_as = tk.StringVar()
        self.__create_input('As', self.rst_kz_ly_as, 4, 7)

        self.rst_kz_ly_min = tk.StringVar()
        self.__create_input('P min', self.rst_kz_ly_min, 4, 8)

        self.rst_kz_ly_as_min = tk.StringVar()
        self.__create_input('As min', self.rst_kz_ly_as_min, 4, 9)

        self.rst_kz_ly_as_xp = tk.StringVar()
        self.__create_input('XP', self.rst_kz_ly_as_xp, 4, 10)

        self.rst_kz_ly_as_sp = tk.StringVar()
        self.__create_input('SP', self.rst_kz_ly_as_sp, 4, 11)

        # result: zz-lx
        self.rst_zz_lx_m = tk.StringVar()
        self.__create_input('Mx', self.rst_zz_lx_m, 5, 2)

        self.rst_zz_lx_h0 = tk.StringVar()
        self.__create_input('H0', self.rst_zz_lx_h0, 5, 3)

        self.rst_zz_lx_b = tk.StringVar()
        self.__create_input('B', self.rst_zz_lx_b, 5, 4)

        self.rst_zz_lx_x = tk.StringVar()
        self.__create_input('X', self.rst_zz_lx_x, 5, 5)

        self.rst_zz_lx_ebh0 = tk.StringVar()
        self.__create_input('EBH0', self.rst_zz_lx_ebh0, 5, 6)

        self.rst_zz_lx_as = tk.StringVar()
        self.__create_input('As', self.rst_zz_lx_as, 5, 7)

        self.rst_zz_lx_min = tk.StringVar()
        self.__create_input('P min', self.rst_zz_lx_min, 5, 8)

        self.rst_zz_lx_as_min = tk.StringVar()
        self.__create_input('As min', self.rst_zz_lx_as_min, 5, 9)

        self.rst_zz_lx_as_xp = tk.StringVar()
        self.__create_input('XP', self.rst_zz_lx_as_xp, 5, 10)

        self.rst_zz_lx_as_sp = tk.StringVar()
        self.__create_input('SP', self.rst_zz_lx_as_sp, 5, 11)

        # result: zz-ly
        self.rst_zz_ly_m = tk.StringVar()
        self.__create_input('Mx', self.rst_zz_ly_m, 6, 2)

        self.rst_zz_ly_h0 = tk.StringVar()
        self.__create_input('H0', self.rst_zz_ly_h0, 6, 3)

        self.rst_zz_ly_b = tk.StringVar()
        self.__create_input('B', self.rst_zz_ly_b, 6, 4)

        self.rst_zz_ly_x = tk.StringVar()
        self.__create_input('X', self.rst_zz_ly_x, 6, 5)

        self.rst_zz_ly_ebh0 = tk.StringVar()
        self.__create_input('EBH0', self.rst_zz_ly_ebh0, 6, 6)

        self.rst_zz_ly_as = tk.StringVar()
        self.__create_input('As', self.rst_zz_ly_as, 6, 7)

        self.rst_zz_ly_min = tk.StringVar()
        self.__create_input('P min', self.rst_zz_ly_min, 6, 8)

        self.rst_zz_ly_as_min = tk.StringVar()
        self.__create_input('As min', self.rst_zz_ly_as_min, 6, 9)

        self.rst_zz_ly_as_xp = tk.StringVar()
        self.__create_input('XP', self.rst_zz_ly_as_xp, 6, 10)

        self.rst_zz_ly_as_sp = tk.StringVar()
        self.__create_input('SP', self.rst_zz_ly_as_sp, 6, 11)

    def __init_calc_btn(self):
        calc_btn = tk.Button(self, text = 'Calc', command = self.calc)
        calc_btn.grid(row = 7, column = 4)

        export_btn = tk.Button(self, text = 'Export', command = self.export_excel)
        export_btn.grid(row = 7, column = 5)

        exit_btn = tk.Button(self, text = 'Exit', command = self.quit)
        exit_btn.grid(row = 7, column = 6)

    def calc(self):
        g = float(self.var_g.get())
        q = float(self.var_q.get())
        h = float(self.var_h.get())
        lx = float(self.var_lx.get())
        ly = float(self.var_ly.get())
        lx_d_ly_top = float(self.var_lx_d_ly[0].get())
        lx_d_ly_bot = float(self.var_lx_d_ly[1].get())
        lx_d_ly_dec = round(lx_d_ly_bot - lx_d_ly_top, 2)

        kx1_top = float(self.var_kx1[0].get())
        kx1_bot = float(self.var_kx1[1].get())
        kx1 = kx1_top + ((kx1_bot - kx1_top) / lx_d_ly_dec * (lx / ly - lx_d_ly_top))
        self.var_kx1[2].set(round(kx1, 5))
        
        ky1_top = float(self.var_ky1[0].get())
        ky1_bot = float(self.var_ky1[1].get())
        ky1 = ky1_top + ((ky1_bot - ky1_top) / lx_d_ly_dec * (lx / ly - lx_d_ly_top))
        self.var_ky1[2].set(round(ky1, 5))

        kx2_top = float(self.var_kx2[0].get())
        kx2_bot = float(self.var_kx2[1].get())
        kx2 = kx2_top + ((kx2_bot - kx2_top) / lx_d_ly_dec * (lx / ly - lx_d_ly_top))
        self.var_kx2[2].set(round(kx2, 5))

        ky2_top = float(self.var_ky2[0].get())
        ky2_bot = float(self.var_ky2[1].get())
        ky2 = ky2_top + ((ky2_bot - ky2_top) / lx_d_ly_dec * (lx / ly - lx_d_ly_top))
        self.var_ky2[2].set(round(ky2, 5))

        kx0_top = float(self.var_kx0[0].get())
        kx0_bot = float(self.var_kx0[1].get())
        kx0 = kx0_top + ((kx0_bot - kx0_top) / lx_d_ly_dec * (lx / ly - lx_d_ly_top))
        self.var_kx0[2].set(round(kx0, 5))

        ky0_top = float(self.var_ky0[0].get())
        ky0_bot = float(self.var_ky0[1].get())
        ky0 = ky0_top + ((ky0_bot - ky0_top) / lx_d_ly_dec * (lx / ly - lx_d_ly_top))
        self.var_ky0[2].set(round(ky0, 5))

        kx1 = round(kx1, 5)
        ky1 = round(ky1, 5)
        kx2 = round(kx2, 5)
        ky2 = round(ky2, 5)
        kx0 = round(kx0, 5)
        ky0 = round(ky0, 5)

        mx1 = round(kx1 * (g + q / 2) * (lx / 1000) * (lx / 1000), 3)
        my1 = round(ky1 * (g + q / 2) * (lx / 1000) * (lx / 1000), 3)

        mx2 = round(kx2 * (q / 2) * (lx / 1000) * (lx / 1000), 3)
        my2 = round(ky2 * (q / 2) * (lx / 1000) * (lx / 1000), 3)

        mx0 = round(kx0 * (g + q) * (lx / 1000) * (lx / 1000), 3)
        my0 = round(ky0 * (g + q) * (lx / 1000) * (lx / 1000), 3)

        mx = round(mx1 + mx2, 3)
        my = round(my1 + my2, 3)

        self.rst_m_x1.set(mx1)
        self.rst_m_y1.set(my1)
        self.rst_m_x2.set(mx2)
        self.rst_m_y2.set(my2)
        self.rst_m_x0.set(mx0)
        self.rst_m_y0.set(my0)
        self.rst_m_x.set(mx)
        self.rst_m_y.set(my)

        self.rst_kz_lx_m.set(mx)
        self.rst_kz_ly_m.set(my)

        mx0 = -mx0
        my0 = -my0

        self.rst_zz_lx_m.set(mx0)
        self.rst_zz_ly_m.set(my0)

        self.rst_kz_lx_h0.set(h - 20)
        self.rst_kz_ly_h0.set(h - 30)
        self.rst_zz_lx_h0.set(h - 20)
        self.rst_zz_ly_h0.set(h - 20)

        b = 1000
        self.rst_kz_lx_b.set(b)
        self.rst_kz_ly_b.set(b)
        self.rst_zz_lx_b.set(b)
        self.rst_zz_ly_b.set(b)

        fc = float(self.var_fc.get())

        kz_lx_h0 = float(self.rst_kz_lx_h0.get())
        kz_lx_x = kz_lx_h0 - math.sqrt(math.pow(kz_lx_h0, 2) - 2 * mx * 1000 / fc)

        kz_ly_h0 = float(self.rst_kz_ly_h0.get())
        kz_ly_x = kz_ly_h0 - math.sqrt(math.pow(kz_ly_h0, 2) - 2 * my * 1000 / fc)

        zz_lx_h0 = float(self.rst_zz_lx_h0.get())
        zz_lx_x = zz_lx_h0 - math.sqrt(math.pow(zz_lx_h0, 2) - 2 * mx0 * 1000 / fc)

        zz_ly_h0 = float(self.rst_zz_ly_h0.get())
        zz_ly_x = zz_ly_h0 - math.sqrt(math.pow(zz_ly_h0, 2) - 2 * my0 * 1000 / fc)

        kz_lx_x = round(kz_lx_x, 3)
        self.rst_kz_lx_x.set(kz_lx_x)

        kz_ly_x = round(kz_ly_x, 3)
        self.rst_kz_ly_x.set(kz_ly_x)

        zz_lx_x = round(zz_lx_x, 3)
        self.rst_zz_lx_x.set(zz_lx_x)

        zz_ly_x = round(zz_ly_x, 3)
        self.rst_zz_ly_x.set(zz_ly_x)

        eb = float(self.var_eb.get())
        self.rst_kz_lx_ebh0.set(round(eb * kz_lx_h0, 2))
        self.rst_kz_ly_ebh0.set(round(eb * kz_ly_h0, 2))
        self.rst_zz_lx_ebh0.set(round(eb * zz_lx_h0, 2))
        self.rst_zz_ly_ebh0.set(round(eb * zz_ly_h0, 2))

        fy = float(self.var_fy.get())
        kz_lx_as = round(fc * b * kz_lx_x / fy, 2)
        self.rst_kz_lx_as.set(kz_lx_as)

        kz_ly_as = round(fc * b * kz_ly_x / fy, 2)
        self.rst_kz_ly_as.set(kz_ly_as)

        zz_lx_as = round(fc * b * zz_lx_x / fy, 2)
        self.rst_zz_lx_as.set(zz_lx_as)

        zz_ly_as = round(fc * b * zz_ly_x / fy, 2)
        self.rst_zz_ly_as.set(zz_ly_as)

        ft = float(self.var_ft.get())
        p_min = round(max(0.002, 0.45 * ft / fy), 6)

        self.rst_kz_lx_min.set(p_min)
        self.rst_kz_ly_min.set(p_min)
        self.rst_zz_lx_min.set(p_min)
        self.rst_zz_ly_min.set(p_min)

        as_min = round(p_min * b * h, 2)
        self.rst_kz_lx_as_min.set(as_min)
        self.rst_kz_ly_as_min.set(as_min)
        self.rst_zz_lx_as_min.set(as_min)
        self.rst_zz_ly_as_min.set(as_min)

        kz_lx_as_max = max(as_min, kz_lx_as)
        kz_ly_as_max = max(as_min, kz_ly_as)
        zz_lx_as_max = max(as_min, zz_lx_as)
        zz_ly_as_max = max(as_min, zz_ly_as)

        pj_lib = OrderedDict([
            (251, [ 8, 200 ]), (265, [ 8, 190 ]), (279, [ 8, 180 ]), (296, [ 8, 170 ]), (314, [ 8, 160 ]), (335, [ 8, 150 ]),
            (393, [ 10, 200 ]), (413, [ 10, 190 ]), (436, [ 10, 180 ]), (462, [ 10, 170 ]), (491, [ 10, 160 ]), (523, [ 10, 150 ]),
        ])

        # kz_lx
        for pj_mj in pj_lib:
            if pj_mj > kz_lx_as_max:
                zj_jj = pj_lib[pj_mj]
                self.rst_kz_lx_as_xp.set('Φ' + str(zj_jj[0]) + '@' + str(zj_jj[1]))
                self.rst_kz_lx_as_sp.set(pj_mj)
                break;
        else:
            zj_jj = pj_lib[pj_mj]
            self.rst_kz_lx_as_xp.set('Φ' + str(zj_jj[0]) + '@' + str(zj_jj[1]))
            self.rst_kz_lx_as_sp.set(pj_mj)

        # kz_ly
        for pj_mj in pj_lib:
            if pj_mj > kz_ly_as_max:
                zj_jj = pj_lib[pj_mj]
                self.rst_kz_ly_as_xp.set('Φ' + str(zj_jj[0]) + '@' + str(zj_jj[1]))
                self.rst_kz_ly_as_sp.set(pj_mj)
                break;
        else:
            zj_jj = pj_lib[pj_mj]
            self.rst_kz_ly_as_xp.set('Φ' + str(zj_jj[0]) + '@' + str(zj_jj[1]))
            self.rst_kz_ly_as_sp.set(pj_mj)

        # zz_lx
        for pj_mj in pj_lib:
            if pj_mj > zz_lx_as_max:
                zj_jj = pj_lib[pj_mj]
                self.rst_zz_lx_as_xp.set('Φ' + str(zj_jj[0]) + '@' + str(zj_jj[1]))
                self.rst_zz_lx_as_sp.set(pj_mj)
                break;
        else:
            zj_jj = pj_lib[pj_mj]
            self.rst_zz_lx_as_xp.set('Φ' + str(zj_jj[0]) + '@' + str(zj_jj[1]))
            self.rst_zz_lx_as_sp.set(pj_mj)

        # zz_ly
        for pj_mj in pj_lib:
            if pj_mj > zz_ly_as_max:
                zj_jj = pj_lib[pj_mj]
                self.rst_zz_ly_as_xp.set('Φ' + str(zj_jj[0]) + '@' + str(zj_jj[1]))
                self.rst_zz_ly_as_sp.set(pj_mj)
                break;
        else:
            zj_jj = pj_lib[pj_mj]
            self.rst_zz_ly_as_xp.set('Φ' + str(zj_jj[0]) + '@' + str(zj_jj[1]))
            self.rst_zz_ly_as_sp.set(pj_mj)

        # export
        worksheet = self.workbook.add_worksheet()
        # merge format
        merge_format = self.workbook.add_format({
            'align': 'center',
            'valign': 'vcenter',
        })

        # title
        worksheet.merge_range('A1:F1', '楼板内力计算表', merge_format)

        worksheet.write('A2', '楼板编号', merge_format)
        worksheet.write('B2', 'Lb{}'.format(self.auto_inc), merge_format)
        worksheet.write('C2', 'G', merge_format)
        worksheet.write('D2', g, merge_format)
        worksheet.write('E2', 'Q', merge_format)
        worksheet.write('F2', q, merge_format)

        self.auto_inc += 1

        worksheet.write('A3', 'Lx', merge_format)
        worksheet.write('B3', lx, merge_format)
        worksheet.write('C3', 'Ly', merge_format)
        worksheet.write('D3', ly, merge_format)
        worksheet.write('E3', 'h', merge_format)
        worksheet.write('F3', h, merge_format)

        worksheet.write('A4', 'Lx/Ly', merge_format)
        worksheet.write('B4', round(lx / ly, 2), merge_format)
        worksheet.write('C4', 'Fc', merge_format)
        worksheet.write('D4', fc, merge_format)
        worksheet.write('E4', 'Fy', merge_format)
        worksheet.write('F4', fy, merge_format)

        double_lx = round(lx * lx / 1000 / 1000, 3)

        worksheet.merge_range('A5:F5', 'Mx1 = Kx1*(g+q/2)*lx*lx = {}*{}*{}={}'.format(kx1, round(g + q / 2, 2), double_lx , mx1), merge_format)
        worksheet.merge_range('A6:F6', 'My1 = Ky1*(g+q/2)*lx*lx = {}*{}*{}={}'.format(ky1, round(g + q / 2, 2), double_lx, my1), merge_format)
        worksheet.merge_range('A7:F7', 'Mx2 = Kx2*(q/2)*lx*lx = {}*{}*{}={}'.format(kx2, round(q / 2, 2), double_lx, mx2), merge_format)
        worksheet.merge_range('A8:F8', 'My2 = Ky2*(q/2)*lx*lx = {}*{}*{}={}'.format(ky2, round(q / 2, 2), double_lx, my2), merge_format)
        worksheet.merge_range('A9:F9', 'Mx0 = Kx0*(g+q)*lx*lx = {}*{}*{}={}'.format(kx0, round(g + q, 2), double_lx, mx0), merge_format)
        worksheet.merge_range('A10:F10', 'My0 = Ky0*(g+q)*lx*lx = {}*{}*{}={}'.format(ky0, round(g + q, 2), double_lx, my0), merge_format)
        worksheet.merge_range('A11:F11', 'Mx = Mx1 + Mx2 = {} + {} = {}'.format(mx1, mx2, mx), merge_format)
        worksheet.merge_range('A12:F12', 'My = My1 + My2 = {} + {} = {}'.format(my1, my2, my), merge_format)

        worksheet.merge_range('A13:B13', '', merge_format)
        worksheet.merge_range('C13:D13', '跨中', merge_format)
        worksheet.merge_range('E13:F13', '支座', merge_format)

        worksheet.merge_range('A14:B14', '方向', merge_format)
        worksheet.merge_range('C14:D14', 'Lx', merge_format)
        worksheet.merge_range('E14:F14', 'Ly', merge_format)

        worksheet.merge_range('A15:B15', 'M(KN)', merge_format)
        worksheet.write('C15', mx, merge_format)
        worksheet.write('D15', my, merge_format)
        worksheet.write('E15', mx0, merge_format)
        worksheet.write('F15', my0, merge_format)

        worksheet.merge_range('A16:B16', 'h(mm)', merge_format)
        worksheet.write('C16', h, merge_format)
        worksheet.write('D16', h, merge_format)
        worksheet.write('E16', h, merge_format)
        worksheet.write('F16', h, merge_format)

        worksheet.merge_range('A17:B17', 'b(mm)', merge_format)
        worksheet.write('C17', b, merge_format)
        worksheet.write('D17', b, merge_format)
        worksheet.write('E17', b, merge_format)
        worksheet.write('F17', b, merge_format)

        worksheet.merge_range('A18:B18', 'x(mm)', merge_format)
        worksheet.write('C18', kz_lx_x, merge_format)
        worksheet.write('D18', kz_ly_x, merge_format)
        worksheet.write('E18', zz_lx_x, merge_format)
        worksheet.write('F18', zz_ly_x, merge_format)

        worksheet.merge_range('A19:B19', 'Ebho(mm)', merge_format)
        worksheet.write('C19', self.rst_kz_lx_ebh0.get(), merge_format)
        worksheet.write('D19', self.rst_kz_ly_ebh0.get(), merge_format)
        worksheet.write('E19', self.rst_zz_lx_ebh0.get(), merge_format)
        worksheet.write('F19', self.rst_zz_ly_ebh0.get(), merge_format)

        worksheet.merge_range('A20:B20', 'As(mm2)', merge_format)
        worksheet.write('C20', self.rst_kz_lx_as.get(), merge_format)
        worksheet.write('D20', self.rst_kz_ly_as.get(), merge_format)
        worksheet.write('E20', self.rst_zz_lx_as.get(), merge_format)
        worksheet.write('F20', self.rst_zz_ly_as.get(), merge_format)

        worksheet.merge_range('A21:B21', 'Pmin', merge_format)
        worksheet.write('C21', self.rst_kz_lx_min.get(), merge_format)
        worksheet.write('D21', self.rst_kz_ly_min.get(), merge_format)
        worksheet.write('E21', self.rst_zz_lx_min.get(), merge_format)
        worksheet.write('F21', self.rst_zz_ly_min.get(), merge_format)

        worksheet.merge_range('A22:B22', 'Pmin*bh(mm2)', merge_format)
        worksheet.write('C22', self.rst_kz_lx_as_min.get(), merge_format)
        worksheet.write('D22', self.rst_kz_ly_as_min.get(), merge_format)
        worksheet.write('E22', self.rst_zz_lx_as_min.get(), merge_format)
        worksheet.write('F22', self.rst_zz_ly_as_min.get(), merge_format)

        worksheet.merge_range('A23:B23', '选配钢筋', merge_format)
        worksheet.write('C23', self.rst_kz_lx_as_xp.get(), merge_format)
        worksheet.write('D23', self.rst_kz_ly_as_xp.get(), merge_format)
        worksheet.write('E23', self.rst_zz_lx_as_xp.get(), merge_format)
        worksheet.write('F23', self.rst_zz_ly_as_xp.get(), merge_format)

        worksheet.merge_range('A24:B24', '实配面积', merge_format)
        worksheet.write('C24', self.rst_kz_lx_as_sp.get(), merge_format)
        worksheet.write('D24', self.rst_kz_ly_as_sp.get(), merge_format)
        worksheet.write('E24', self.rst_zz_lx_as_sp.get(), merge_format)
        worksheet.write('F24', self.rst_zz_ly_as_sp.get(), merge_format)

    def export_excel(self):
        self.workbook.close()

    def __create_input(self, text, text_var, row, column, read_only = True, callback = None):
        frame = tk.Frame(self)
        label = tk.Label(frame, text = text)

        if callback is not None:
            entry = tk.Entry(frame, textvariable = text_var, validate = 'focusout', validatecommand = callback)
        else:
            entry = tk.Entry(frame, textvariable = text_var)

        if (read_only is True):
            entry['state'] = 'readonly'

        label.pack(side = tk.LEFT, padx = 3, pady = 3)
        entry.pack(side = tk.LEFT, padx = 3, pady = 3)
        frame.grid(row = row, column = column, padx = 5)

        return entry

    def __create_k_val(self, text, text_vars, row, column, read_only = False, callback_top = None, callback_bot = None):
        frame = tk.Frame(self)
        label = tk.Label(frame, text = text)

        if callback_top is not None:
            entry_top = tk.Entry(frame, textvariable = text_vars[0], validate = 'focusout', validatecommand = callback_top)
        else:
            entry_top = tk.Entry(frame, textvariable = text_vars[0])

        if callback_bot is not None:
            entry_bot = tk.Entry(frame, textvariable = text_vars[1], validate = 'focusout', validatecommand = callback_bot)
        entry_bot = tk.Entry(frame, textvariable = text_vars[1])

        entry_rst = tk.Entry(frame, textvariable = text_vars[2], state = 'readonly')

        if read_only is True:
            entry_bot['state'] = 'readonly'
            entry_top['state'] = 'readonly'

        label.pack(side = tk.TOP, padx = 3, pady = 3)
        entry_top.pack(side = tk.TOP, padx = 3, pady = 3)
        entry_bot.pack(side = tk.TOP, padx = 3, pady = 3)
        entry_rst.pack(side = tk.TOP, padx = 3, pady = 3)
        frame.grid(row = row, column = column, padx = 5)

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
