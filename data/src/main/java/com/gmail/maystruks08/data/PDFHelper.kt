package com.gmail.maystruks08.data

import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.domain.CalendarHelper
import com.gmail.maystruks08.domain.entity.*
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import com.itextpdf.text.Phrase


class PDFHelper @Inject constructor(private val calendarHelper: CalendarHelper) {

    fun exportPurchaseList(fileName: String, purchaseList: PurchaseList) {
        val document = createDocument("Список_$fileName")
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
        val document = createDocument("Список_$fileName")
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


    fun exportMenu(menu: Menu) {
        val document = createDocument("Раскладка_" + menu.name)
        try {
            val table = PdfPTable(1)
            table.addCell( createPdfPCell("${menu.name} ${calendarHelper.format(menu.dateOfStartMenu, CalendarHelper.DATE_FORMAT)}.${menu.startFrom.title} старт раскладки", Element.ALIGN_CENTER, true))
            menu.days.forEach { table.addCell(createDayTable(it)) }
            document.add(table)
        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:$de")
        } finally {
            document.close()
        }
    }

    private fun createDayTable(day: Day): PdfPTable {
        return PdfPTable(3).apply {
            val cellHeader = createPdfPCell("День ${day.number} ${calendarHelper.format(day.date, CalendarHelper.DATE_FORMAT)}. Вес на одного ${day.getDayTotalWeightForOne()}. Вес на всех ${day.getDayTotalWeightForAll()}", Element.ALIGN_CENTER, true).apply { colspan = 3 }
            this.addCell(cellHeader)

            TypeOfMeal.values().forEach {
                val foodReceptionTable = createFoodReceiptTable(it, day.products[it].orEmpty())
                this.addCell(foodReceptionTable)
            }
        }
    }

    private fun createFoodReceiptTable(typeOfMeal: TypeOfMeal, products: List<Product>): PdfPTable {
        return PdfPTable(3).apply {
            if(products.isEmpty()){
                addCell(createPdfPCell("", Element.ALIGN_LEFT).apply { borderWidth = 0f })
                addCell(createPdfPCell("", Element.ALIGN_LEFT).apply { borderWidth = 0f })
                addCell(createPdfPCell("", Element.ALIGN_LEFT).apply { borderWidth = 0f })
                return this
            }

            addCell(createPdfPCell(typeOfMeal.title, Element.ALIGN_LEFT, true))
            addCell(createPdfPCell("На 1", Element.ALIGN_RIGHT, true))
            addCell(createPdfPCell("На всех", Element.ALIGN_RIGHT, true))

            products.forEach { product ->
                addCell(createPdfPCell(product.name, Element.ALIGN_LEFT))
                addCell(createPdfPCell(product.portion.value.toString(), Element.ALIGN_RIGHT))
                addCell(createPdfPCell(product.portion.portionForAllPeople.toString(), Element.ALIGN_RIGHT))
                if(product is ProductSet){
                    product.products.forEach {
                        addCell(createPdfPCell(it.name, Element.ALIGN_LEFT))
                        addCell(createPdfPCell(it.portion.value.toString(), Element.ALIGN_RIGHT))
                        addCell(createPdfPCell(it.portion.portionForAllPeople.toString(), Element.ALIGN_RIGHT))
                    }
                }
            }
        }
    }

    private fun createDocument(fileName: String): Document {
        return Document(PageSize.A2, 50f, 50f, 50f, 50f).apply {
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
        val font = if (isTitle) headerFont else normalFont
        return PdfPCell(Phrase(text, font)).apply { horizontalAlignment = align }
    }

    companion object {

        const val FONT = "/assets/fonts/arial_bold.ttf"

        const val FONT_BOLD = "/assets/fonts/arial_bold.ttf"

        val headerFont = Font(
            BaseFont.createFont(FONT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED),
            16.0f,
            Font.NORMAL,
            BaseColor.BLACK
        )

        val normalFont = Font(
            BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED),
            12.0f,
            Font.NORMAL,
            BaseColor.BLACK
        )
    }
}