//
//  HikeDetail.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/15.
//

import SwiftUI

struct HikeDetail: View {
    var hike: Hike
    @State private var dataToShow: KeyPath<Hike.Observation, Range<Double>> = \.elevation
    
    static let buttons = [
        ("Elevation", \Hike.Observation.elevation),
        ("Heart Rate", \Hike.Observation.heartRate),
        ("Pace", \Hike.Observation.pace)
    ]
    
    var body: some View {
        VStack {
            HikeGraph(hike: hike, path: dataToShow)
                .frame(height: 250)
            
            HStack(spacing: 25) {
                ForEach(Array(Self.buttons.enumerated()), id: \.offset) { index, button in
                    Button(action: {
                        dataToShow = button.1
                    }, label: {
                        Text(button.0)
                            .font(.system(size: 15))
                            .foregroundStyle(dataToShow == button.1 ? .gray : .accentColor)
                    })
                    .disabled(dataToShow == button.1)
                }
            }
        }
    }
}

#Preview {
    let hike = ModelData().hikes[0]
    
    HikeDetail(hike: hike)
}
