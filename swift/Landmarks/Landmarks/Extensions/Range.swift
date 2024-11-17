//
//  Range.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/15.
//

import Foundation

extension Range where Bound: BinaryFloatingPoint {
    
    // Calculates the "magnitude" of the range.
    func magnitude() -> Double {
        Double(upperBound - lowerBound)
    }

}
