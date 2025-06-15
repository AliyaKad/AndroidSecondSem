package ru.itis.AndroidSecondSem.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.itis.AndroidSecondSem.R
import ru.itis.AndroidSecondSem.view.CircularDiagramView


class DiagramFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diagram, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diagramView = view.findViewById<CircularDiagramView>(R.id.circularDiagramView)

        diagramView.setSectorValues(listOf(30f, 45f, 85f))
        diagramView.setSectorColors(
            listOf(
                ContextCompat.getColor(requireContext(), R.color.DARKGREEN),
                ContextCompat.getColor(requireContext(), R.color.GREEN),
                ContextCompat.getColor(requireContext(), R.color.LIGHTGREEN)
            )
        )
        diagramView.setInnerRadius(300f)
        diagramView.setRingWidth(30f)
    }
}