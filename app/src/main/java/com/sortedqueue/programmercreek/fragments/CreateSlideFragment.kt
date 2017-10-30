package com.sortedqueue.programmercreek.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.LanguageRecyclerAdapter
import com.sortedqueue.programmercreek.constants.LanguageConstants
import com.sortedqueue.programmercreek.database.SlideModel
import com.sortedqueue.programmercreek.database.firebase.Code
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.database.firebase.FirebaseStorageHandler
import com.sortedqueue.programmercreek.interfaces.PresentationCommunicationsListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.FileUtils
import com.sortedqueue.programmercreek.util.PermissionUtils
/*import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;*/

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date



//import id.zelory.compressor.Compressor;

import com.facebook.GraphRequest.TAG

/**
 * Created by Alok on 06/04/17.
 */

class CreateSlideFragment : Fragment(), View.OnClickListener, AuxilaryUtils.PhotoOptionListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.titleEditText)
    internal var titleEditText: EditText? = null
    @BindView(R.id.subTitleEditText)
    internal var subTitleEditText: EditText? = null
    @BindView(R.id.slideImageView)
    internal var slideImageView: ImageView? = null
    @BindView(R.id.slideImageLayout)
    internal var slideImageLayout: FrameLayout? = null
    private val ACTION_CAMERA = 1
    private val ACTION_GALLERY = 2
    @BindView(R.id.deleteImageView)
    internal var deleteImageView: ImageView? = null
    @BindView(R.id.changeImageView)
    internal var changeImageView: ImageView? = null
    @BindView(R.id.rotateImageView)
    internal var rotateImageView: ImageView? = null
    @BindView(R.id.saveImageView)
    internal var saveImageView: ImageView? = null
    @BindView(R.id.doneButton)
    internal var doneButton: Button? = null
    @BindView(R.id.uploadProgressBar)
    internal var uploadProgressBar: ProgressBar? = null
    @BindView(R.id.languageTextView)
    internal var languageTextView: TextView? = null
    @BindView(R.id.importFromFileTextView)
    internal var importFromFileTextView: TextView? = null
    @BindView(R.id.hintPhotoTextView)
    internal var hintPhotoTextView: TextView? = null
    @BindView(R.id.languageRecyclerView)
    internal var languageRecyclerView: RecyclerView? = null
    @BindView(R.id.importLayout)
    internal var importLayout: LinearLayout? = null
    @BindView(R.id.codeEditRecyclerView)
    internal var codeEditRecyclerView: RecyclerView? = null

    private var selectedImageUri: Uri? = null
    private var selectedBitmap: Bitmap? = null
    private var firebaseDatabaseHandler: FirebaseDatabaseHandler? = null
    private var code = ""
    private var presentationCommunicationsListener: PresentationCommunicationsListener? = null
    private var slideModel: SlideModel? = null
    private var languages: ArrayList<String>? = null
    private var languageRecyclerAdapter: LanguageRecyclerAdapter? = null
    private var programCode: Code? = null
    private var codeEditorRecyclerAdapter: CodeEditorRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_slide, container, false)

        slideModel = SlideModel()
        firebaseDatabaseHandler = FirebaseDatabaseHandler(context)
        titleEditText!!.clearFocus()
        subTitleEditText!!.clearFocus()
        deleteImageView!!.setOnClickListener(this)
        changeImageView!!.setOnClickListener(this)
        rotateImageView!!.setOnClickListener(this)
        saveImageView!!.setOnClickListener(this)
        slideImageLayout!!.setOnClickListener(this)
        doneButton!!.setOnClickListener(this)
        languageTextView!!.setOnClickListener(this)
        importFromFileTextView!!.setOnClickListener(this)
        importLayout!!.visibility = View.GONE
        codeEditRecyclerView!!.visibility = View.GONE
        setupLanguageRecyclerView()
        setupRecyclerView()
        return view
    }

    private fun setupLanguageRecyclerView() {
        languageRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        languages = ArrayList<String>()
        languages!!.add("C")
        languages!!.add("C++")
        languages!!.add("Java")
        languageRecyclerAdapter = LanguageRecyclerAdapter(languages!!, this)
        languageRecyclerView!!.adapter = languageRecyclerAdapter
        languageRecyclerAdapter!!.selectedLanguage = languageTextView!!.text.toString().trim { it <= ' ' }
        selectedLanguage = languageRecyclerAdapter!!.selectedLanguage
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PresentationCommunicationsListener) {
            presentationCommunicationsListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        presentationCommunicationsListener = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.changeImageView -> startCropPhotoActivity(selectedImageUri!!)
            R.id.deleteImageView -> {
                hintPhotoTextView!!.visibility = View.VISIBLE
                slideModel!!.slideImageUrl = null
                slideImageView!!.setImageBitmap(null)
                slideImageLayout!!.visibility = View.GONE
            }
            R.id.rotateImageView -> rotateImage()
            R.id.saveImageView -> uploadFileToFirebase(selectedImageUri)
            R.id.doneButton -> save()
            R.id.languageTextView -> if (languageRecyclerView!!.visibility == View.GONE) {
                AnimationUtils.enterReveal(languageRecyclerView)
            } else {
                AnimationUtils.exitRevealGone(languageRecyclerView)
            }
            R.id.importFromFileTextView -> importFromFile()
            R.id.slideImageLayout -> checkPhotoPermissions()
        }

    }

    private val REQUEST_CODE_SEARCH = 1000

    private fun importFromFile() {
        if (PermissionUtils.checkSelfPermission(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1000)) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/plain"
            startActivityForResult(Intent.createChooser(intent,
                    "Load file from directory"), REQUEST_CODE_SEARCH)
        }
    }


    private fun save() {
        var imageUrl: String? = null
        if (selectedImageUri != null) {
            imageUrl = selectedImageUri!!.toString()
        }
        if (codeEditRecyclerView!!.visibility == View.VISIBLE) {
            code = codeEditorRecyclerAdapter!!.code
        }
        slideModel = SlideModel(null, code, titleEditText!!.text.toString(), subTitleEditText!!.text.toString(), imageUrl)
        val presentationPushId = firebaseDatabaseHandler!!.writeSlide(slideModel!!)
        presentationCommunicationsListener!!.onPresentationCreation(presentationPushId, slideModel)
    }

    private fun rotateImage() {
        if (selectedBitmap != null) {
            val matrix = Matrix()
            matrix.postRotate(90f)
            selectedBitmap = Bitmap.createBitmap(selectedBitmap!!, 0, 0, selectedBitmap!!.width, selectedBitmap!!.height,
                    matrix, true)
            slideImageView!!.setImageBitmap(selectedBitmap)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            if (PermissionUtils.checkDeniedPermissions(activity as AppCompatActivity, permissions).size == 0) {
                importFromFile()
            } else {
                if (permissions.size == 3) {
                    Toast.makeText(context, "Some permissions were denied", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == PermissionUtils.PERMISSION_REQUEST) {
            if (PermissionUtils.checkDeniedPermissions(activity as AppCompatActivity, permissions).size == 0) {
                AuxilaryUtils.displayPhotoDialog(context, this)
            } else {
                if (permissions.size == 3) {
                    CommonUtils.displaySnackBar(activity, R.string.camera_read_write_storage_permission_to_open_gallery)
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onChoiceSelected(choice: Int) {
        when (choice) {
            AuxilaryUtils.CHOICE_CAMERA -> loadCamera()
            AuxilaryUtils.CHOICE_GALLERY -> loadGallery()
        }
    }

    private fun loadCamera() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                CommonUtils.displayToast(context, "Unable to open Camera")
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(context,
                        "com.sortedqueue.programmercreek.fileprovider",
                        photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, ACTION_CAMERA)
            }
        }

    }

    private fun loadGallery() {
        val intent: Intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
        } else {
            intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTION_GALLERY)
    }

    internal var mCurrentPhotoPath: String? = null

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "PNG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".png", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            compressAndCropPhoto(mCurrentPhotoPath!!)
        }
    }

    private fun compressAndCropPhoto(mCurrentPhotoPath: String) {
        /* Compressor compressor = new Compressor.Builder(getContext())
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .setQuality(100)
                .build();
        Uri compressedImageUri = Uri.fromFile(compressor.compressToFile(new File(mCurrentPhotoPath)));
        startCropPhotoActivity(compressedImageUri);*/
    }

    private fun startCropPhotoActivity(selectedImageUri: Uri) {
        /*if( selectedImageUri != null ) {
            CropImage.activity(selectedImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                *//*.setFixAspectRatio(true)
                .setAspectRatio(16, 9)*//*
                    .start(getContext(), this);
        }*/
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            ACTION_CAMERA -> if (resultCode == AppCompatActivity.RESULT_OK) {
                handleBigCameraPhoto()
            }
            ACTION_GALLERY -> if (resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImageUri = data!!.data
                compressAndCropPhoto(AuxilaryUtils.getFilePath(context, selectedImageUri!!))
            }
        }/*case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (result != null) {
                        hintPhotoTextView.setVisibility(View.GONE);
                        selectedImageUri = result.getUri();
                        Glide.with(getContext()).load(result.getUri()).asBitmap().listener(new RequestListener<Uri, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, Uri model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Uri model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                selectedBitmap = resource;
                                return false;
                            }
                        }).fitCenter().into(slideImageView);
                    } else {
                        CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_crop_image);
                    }
                }
                break;*/

        if (requestCode == REQUEST_CODE_SEARCH && resultCode == AppCompatActivity.RESULT_OK) {
            try {
                val uri = data!!.data
                if (uri != null) {

                    Log.d(TAG, "File Uri : " + uri.encodedPath + " Path " + uri.path)
                    val filepath = FileUtils.getPath(context, uri)
                    Log.d(TAG, "File path : " + filepath!!)
                    val fis = FileInputStream(filepath)
                    val isr = InputStreamReader(fis, Charset.forName("UTF-8"))
                    val br = BufferedReader(isr)
                    var line: String
                    var completeLine = ""
                    line = br.readLine()
                    while ((line) != null) {
                        completeLine += line.trim { it <= ' ' } + " "
                        line = br.readLine()
                    }
                } else {
                }
                // Rest of code that converts txt file's content into arraylist
            } catch (e: IOException) {
                e.printStackTrace()
                // Codes that handles IOException
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uploadFileToFirebase(selectedImageUri: Uri?) {

        uploadProgressBar!!.visibility = View.VISIBLE
        if (selectedImageUri != null) {
            FirebaseStorageHandler(context).uploadSlideImage(selectedImageUri, object : FirebaseStorageHandler.FileUploadListener {
                override fun onSuccess(downloadUri: Uri) {
                    uploadProgressBar!!.visibility = View.GONE
                    CommonUtils.displayToast(context, "Success")
                    Log.d(TAG, "Upload Success : " + downloadUri.toString())
                    this@CreateSlideFragment.selectedImageUri = downloadUri
                    slideModel!!.slideImageUrl = downloadUri.toString()
                    Glide.with(context).load(downloadUri).asBitmap().into(slideImageView!!)
                }

                override fun onProgressUpdate(currentProgress: Double) {
                    uploadProgressBar!!.progress = currentProgress.toInt()
                }

                override fun onError(e: Exception?) {
                    uploadProgressBar!!.visibility = View.GONE
                    if (e != null) {
                        CommonUtils.displayToast(context, "Error occurred while upload : " + e.message)
                        e.printStackTrace()
                    }
                }
            })
        } else {
            uploadProgressBar!!.visibility = View.GONE
            CommonUtils.displaySnackBar(activity, R.string.image_upload_failed)
        }

    }

    val isPhotoVisible: Boolean
        get() = slideImageLayout!!.visibility == View.VISIBLE

    val isCodeVisible: Boolean
        get() = codeEditRecyclerView!!.visibility == View.VISIBLE

    fun insertCode() {
        if (importLayout!!.visibility != View.VISIBLE) {
            AnimationUtils.enterReveal(importLayout)
            AnimationUtils.enterReveal(codeEditRecyclerView)
        } else {
            AnimationUtils.exitRevealGone(importLayout)
            AnimationUtils.exitRevealGone(codeEditRecyclerView)
        }
    }

    fun insertPhoto() {
        if (slideImageLayout!!.visibility == View.VISIBLE) {
            slideImageLayout!!.visibility = View.GONE
            return
        }
        slideImageLayout!!.visibility = View.VISIBLE
        checkPhotoPermissions()
    }

    private fun checkPhotoPermissions() {
        if (PermissionUtils.checkSelfPermission(this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))) {
            AuxilaryUtils.displayPhotoDialog(context, this)
        }
    }

    fun saveImage() {
        if (selectedImageUri != null && slideModel!!.slideImageUrl == null) {
            uploadFileToFirebase(selectedImageUri)
        }
    }

    fun validateContent(): Boolean {
        val title = titleEditText!!.text.toString()
        if (title == null || title.trim { it <= ' ' }.length == 0) {
            titleEditText!!.requestFocus()
            titleEditText!!.error = "Required"
            return false
        }
        val subTitle = subTitleEditText!!.text.toString()
        if (subTitle == null || subTitle.trim { it <= ' ' }.length == 0) {
            subTitleEditText!!.requestFocus()
            subTitleEditText!!.error = "Required"
            return false
        }
        save()
        return true
    }

    private var selectedLanguageIndex: String? = null
    private var codeTemplate: String? = null
    var selectedLanguage: String? = null
        set(selectedLanguage) {
            field = selectedLanguage
            when (selectedLanguage) {
                "C" -> {
                    selectedLanguageIndex = LanguageConstants.C_INDEX
                    codeTemplate = LanguageConstants.C_TEMPLATE
                }
                "C++" -> {
                    selectedLanguageIndex = LanguageConstants.CPP_INDEX
                    codeTemplate = LanguageConstants.CPP_TEMPLATE
                }
                "Java" -> {
                    selectedLanguageIndex = LanguageConstants.JAVA_INDEX
                    codeTemplate = LanguageConstants.JAVA_TEMPLATE
                }
            }

            programCode = Code()
            programCode!!.language = selectedLanguageIndex
            programCode!!.sourceCode = codeTemplate
            setupRecyclerView()
        }

    override fun onItemClick(position: Int) {
        var selectedLanguage = languages!![position]
        languageRecyclerAdapter!!.selectedLanguage = selectedLanguage
        languageTextView!!.text = selectedLanguage
        selectedLanguage = selectedLanguage

        AnimationUtils.exitRevealGone(languageRecyclerView)
    }

    private fun setupRecyclerView() {
        if (programCode != null) {
            val programLines = AuxilaryUtils.splitProgramIntolines(programCode!!.sourceCode)
            codeEditRecyclerView!!.layoutManager = LinearLayoutManager(context)
            codeEditorRecyclerAdapter = CodeEditorRecyclerAdapter(context, programLines, this.selectedLanguage!!)
            codeEditRecyclerView!!.adapter = codeEditorRecyclerAdapter
            codeEditRecyclerView!!.itemAnimator.changeDuration = 0
        }
    }
}
