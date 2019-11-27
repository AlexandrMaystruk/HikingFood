package com.gmail.maystruks08.data

import android.os.Environment
import android.util.Log
import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.domain.entity.PurchaseList
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class PDFHelper @Inject constructor() {

    fun exportPurchaseList(fileName: String, purchaseList: PurchaseList) {
        val document = createDocument(fileName)
        try {
            val table = PdfPTable(2)
            table.setWidths(intArrayOf(2, 1))
            table.addCell(createPdfPCell("Продукты", Element.ALIGN_LEFT, true))
            table.addCell(createPdfPCell("Вес", Element.ALIGN_RIGHT, true))
            purchaseList.purchaseListItems.forEach {
                table.addCell(createPdfPCell(it.product.name, Element.ALIGN_LEFT))
                table.addCell(createPdfPCell(it.totalWeight.toString(), Element.ALIGN_RIGHT))
            }
            document.add(table)
        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:$de")
        } finally {
            document.close()
        }
    }
    fun exportPurchaseListGroupByFoodReception(fileName: String, purchaseList: PurchaseList) {
        val document = createDocument(fileName)
        try {
            val table = PdfPTable(2)
            table.setWidths(intArrayOf(2, 1))
            table.addCell(createPdfPCell("Продукты", Element.ALIGN_LEFT, true))
            table.addCell(createPdfPCell("Вес", Element.ALIGN_RIGHT, true))
            purchaseList.purchaseListItems.forEach {
                table.addCell(createPdfPCell(it.product.name, Element.ALIGN_LEFT))
                table.addCell(createPdfPCell(it.totalWeight.toString(), Element.ALIGN_RIGHT))
            }
            document.add(table)
        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:$de")
        } finally {
            document.close()
        }
    }


    fun exportMenu(fileName: String, menu: Menu) {
        val document = createDocument(fileName)
        try {
            val table = PdfPTable(9)
            table.setWidths(intArrayOf(2, 1))
            table.addCell(createPdfPCell("Продукты", Element.ALIGN_LEFT, true))



        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:$de")
        } finally {
            document.close()
        }

    }

    private fun createDocument(fileName: String): Document {
        return Document(PageSize.A4, 50f, 50f, 50f, 50f).apply {
            try {
                val path = Environment.getExternalStorageDirectory().absolutePath + "/HikingFood"
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val file = File(dir, "$fileName.pdf")
                val fOut = FileOutputStream(file)

                PdfWriter.getInstance(this, fOut)
                open()
                addCreationDate()
                addAuthor("Hiking Food application")
            } catch (de: DocumentException) {
                Log.e("PDFCreator", "DocumentException:$de")
                close()
            } catch (e: IOException) {
                Log.e("PDFCreator", "ioException:$e")
                close()
            }
        }
    }

    private fun createPdfPCell(text: String, align: Int, isTitle: Boolean = false): PdfPCell {
        val font = if (isTitle) {
            Font(
                BaseFont.createFont(FONT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED),
                16.0f,
                Font.NORMAL,
                BaseColor.BLACK
            )
        } else {
            Font(
                BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED),
                12.0f,
                Font.NORMAL,
                BaseColor.BLACK
            )
        }
        return PdfPCell(Phrase(text, font)).apply {
            when (align) {
                Element.ALIGN_LEFT -> {
                    horizontalAlignment = align
                }
                Element.ALIGN_RIGHT -> {
                    horizontalAlignment = align
                }
            }
        }
    }

    companion object {

        const val FONT = "/assets/fonts/arial_bold.ttf"

        const val FONT_BOLD = "/assets/fonts/arial_bold.ttf"

    }
}