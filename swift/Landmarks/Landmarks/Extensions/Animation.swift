//
//  Animation.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import Foundation
import SwiftUI

extension Animation {
    static func ripple(index: Int) -> Animation {
        Animation.spring(dampingFraction: 0.75)
            .speed(1.3)
            .delay(Double(index) * 0.02)
    }
}
