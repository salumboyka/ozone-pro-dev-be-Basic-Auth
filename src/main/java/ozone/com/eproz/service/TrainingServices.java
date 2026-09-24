package ozone.com.eproz.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ozone.com.eproz.dto.AttachmentDtoRequest;
import ozone.com.eproz.dto.AttachmentDtoResponse;
import ozone.com.eproz.dto.PageResponse;
import ozone.com.eproz.dto.TrainingDtoRequest;
import ozone.com.eproz.dto.TrainingDtoResponse;
import ozone.com.eproz.exception.ResourceNotFoundException;
import ozone.com.eproz.model.StaffModel;
import ozone.com.eproz.model.TrainingModel;
import ozone.com.eproz.repository.StaffRepository;
import ozone.com.eproz.repository.TrainingRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainingServices {

	private final TrainingRepository trainingRepository;
	private final StaffRepository staffRepository;
	private final AttachmentStorageService attachmentStorageService;

	@Transactional
	public TrainingDtoResponse createTraining(TrainingDtoRequest request) {
		StaffModel staff = staffRepository.findById(request.staffId())
				.orElseThrow(() -> new ResourceNotFoundException("Staff", request.staffId()));
		String attachmentPath = request.attachment();
		if (request.attachmentFile() != null && !request.attachmentFile().isEmpty()) {
			attachmentPath = attachmentStorageService.store(request.attachmentFile()).filePath();
		}

		TrainingModel training = new TrainingModel(
				request.title(), request.description(), request.provider(), attachmentPath,
				request.startDate(), request.endDate(), staff);
		return TrainingDtoResponse.from(trainingRepository.save(training));
	}

	public PageResponse<TrainingDtoResponse> findAll(UUID staffId, Pageable pageable) {
		Page<TrainingModel> page = staffId == null
				? trainingRepository.findAll(pageable)
				: trainingRepository.findByStaffId(staffId, pageable);
		return PageResponse.from(page.map(TrainingDtoResponse::from));
	}

	public TrainingDtoResponse findById(UUID id) {
		return TrainingDtoResponse.from(getOrThrow(id));
	}

	@Transactional
	public TrainingDtoResponse updateTraining(UUID id, TrainingDtoRequest request) {
		TrainingModel training = getOrThrow(id);
		training.update(request.title(), request.description(), request.provider(), request.attachment(),
				request.startDate(), request.endDate());
		return TrainingDtoResponse.from(training);
	}

	@Transactional
	public AttachmentDtoResponse uploadAttachment(UUID id, AttachmentDtoRequest request) {
		TrainingModel training = getOrThrow(id);
		AttachmentDtoResponse attachment = attachmentStorageService.store(request.file());
		training.setAttachment(attachment.filePath());
		return attachment;
	}

	private TrainingModel getOrThrow(UUID id) {
		return trainingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Training", id));
	}
}
