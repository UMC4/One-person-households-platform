package com.umc.one_person_households_platform.view.groupbuying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.umc.one_person_households_platform.R
import com.umc.one_person_households_platform.databinding.FragmentGroupBuyingBinding
import com.umc.one_person_households_platform.view.common.OnClickInterface
import com.umc.one_person_households_platform.view.common.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest

class GroupBuyingFragment : Fragment(), OnClickInterface {

    private var _binding: FragmentGroupBuyingBinding? = null
    private val binding get() = _binding!!

    private val args: GroupBuyingFragmentArgs by navArgs()
    private val viewModel: GroupBuyingViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupBuyingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    // 초기 설정
    private fun init() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.groupBuying = this

        if (args.category == "마감임박순") {
            binding.rbImminentDeadline.isChecked = true
            onCategoryButtonClick(binding.rbImminentDeadline)
        } else {
            onCategoryButtonClick(binding.rbRecent)
        }
    }

    // 공동 구매 탭 클릭
    fun onCategoryButtonClick(view: View) {
        val adapter = GroupBuyingAdapter(this)
        binding.rvContent.adapter = adapter

        lifecycleScope.launchWhenCreated {
            when (view.id) {
                R.id.rb_recent -> viewModel.getGroupBuyingRecentData()
                R.id.rb_imminent_deadline -> viewModel.getGroupBuyingImminentData()
                R.id.rb_household_goods -> viewModel.getGroupBuyingHouseholdGoodsData()
                R.id.rb_ingredients -> viewModel.getGroupBuyingIngredientsData()
                R.id.rb_etc -> viewModel.getGroupBuyingETCData()
            }

            viewModel.content.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    // 검색 화면 이동
    fun onSearchButtonClick() {
        findNavController().navigate(R.id.action_groupBuyingFragment_to_groupBuyingSearchFragment)
    }

    // 글쓰기 화면 이동
    fun onWriteButtonClick() {
        val action = GroupBuyingFragmentDirections.actionGroupBuyingFragmentToGroupBuyingWriteFragment()
        findNavController().navigate(action)
    }

    // 게시글 상세 화면 이동
    override fun onContentButtonClick(postIdx: Int, category: String) {
        val action = GroupBuyingFragmentDirections.actionGroupBuyingFragmentToGroupBuyingDetailFragment(postIdx)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}