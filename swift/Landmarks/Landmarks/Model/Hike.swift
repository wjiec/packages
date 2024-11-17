//
//  Hike.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/15.
//

import Foundation

struct Hike: Hashable, Codable, Identifiable {
    let id: Int
    let name: String
    let distance: Double
    let difficulty: Int
    var observations: [Observation]
    
    static let formatter = LengthFormatter()
    var distanceText: String {
        Self.formatter.string(fromValue: distance, unit: .kilometer)
    }

    struct Observation: Codable, Hashable {
        let distanceFromStart: Double

        let elevation: Range<Double>
        let pace: Range<Double>
        let heartRate: Range<Double>
    }
}
